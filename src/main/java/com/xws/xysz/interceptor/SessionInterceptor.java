package com.xws.xysz.interceptor;


import com.xws.xysz.model.Manager;
import com.xws.xysz.model.Rights;
import com.xws.xysz.util.DispatcherUtil;
import com.xws.xysz.util.StringUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 杨光 on 2018/2/3.
 */
public class SessionInterceptor extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod) {
            //获得当前页面地址
            String path = request.getServletPath();
            request.setAttribute("nowPath", path);

            // 请求的uri
            String uri = request.getRequestURI();
            if (request.getQueryString() != null) {
                uri += "?" + request.getQueryString();
            }
            uri = uri.replaceAll("/+", "/");

            if (uri.indexOf("/admin/") == 0) {
                return isLogin(request, response, "adminInfo", "/alogin");
            } else if (uri.indexOf("/merchant/") == 0) {
                return isLogin(request, response, "merchantInfo", "/mlogin");
            }else if(uri.indexOf("/channel/") == 0){
                return isLogin(request, response, "channelInfo", "/clogin");
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    //登录校验
    private boolean isLogin(HttpServletRequest request, HttpServletResponse response, String sessionName, String loginPage) {
        Object obj = request.getSession().getAttribute(sessionName);
        String uri = request.getRequestURI();
        if (null == obj) {
            // 如果session中不存在登录者实体，则弹出框提示重新登录
            DispatcherUtil.AlertAndRedirect("登录过期，请重新登录", loginPage, request, response);
            return false;
        } else {
            // 如果session中存在登录者实体，则继续

            if (sessionName.equals("adminInfo")) {
                //管理员
                Manager manager = (Manager) obj;

                //得到当前登录管理员的权限列表
                List<Rights> rightsList = manager.getRightsTree();

                //没当前页面权限
                if (!checkRights(rightsList, uri)) {
                    DispatcherUtil.AlertAndRedirect("您没有当前页面权限", request.getHeader("Referer"), request, response);
                    return false;
                } else {
                    request.getSession().setAttribute("rightsTree", rightsList);
                    return true;
                }
            }else{
                //商户，渠道商
                return true;
            }
        }
    }

    //检查是否拥有权限，并记录权限路径状态
    private boolean checkRights(List<Rights> rightsList, String uri) {
        boolean flag = false;
        Pattern pattern = null;
        for (Rights rights : rightsList) {
            boolean isEnter = false;
            if (!StringUtil.isEmpty(rights.getRight())) {
                String right = rights.getRight();
                pattern = Pattern.compile(right);
                Matcher m = pattern.matcher(uri);
                if (m.find()) {
                    rights.setIsEnter(true);
                    flag = true;
                    isEnter = true;
                }
            }
            if (rights.getChildren() != null) {
                if (checkRights(rights.getChildren(), uri)) {
                    rights.setIsEnter(true);
                    flag = true;
                    isEnter = true;
                }
            }
            rights.setIsEnter(isEnter);
        }
        return flag;
    }
}
