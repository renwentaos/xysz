package com.xws.xysz.interceptor;


import com.google.gson.GsonBuilder;
import com.xws.xysz.config.APPConfig;
import com.xws.xysz.enums.APICode;
import com.xws.xysz.model.APPResult;
import com.xws.xysz.util.IPUtil;
import com.xws.xysz.util.MD5Util;
import com.xws.xysz.util.RandomUtil;
import com.xws.xysz.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * Created by 杨光 on 2017/7/6.
 */
@Slf4j(topic = "appLogger")
public class AppInterceptor implements HandlerInterceptor {
    @Autowired
    private  StringRedisTemplate stringRedisTemplate;
//    @Autowired
//    MemberService memberService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        if (o instanceof ResourceHttpRequestHandler) {
            //非controller里的直接跳转
            return true;
        } else {
            log(request); //日志记录
            //验证
            APPResult appResult = requestVerify(request);
            if (appResult != null) {
                responseWrite(request,response, appResult);
                return false;
            }

            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            Object obj = modelAndView.getModel().get("APPResult");
            if (obj != null) {
                APPResult appResult = (APPResult) obj;
                String memberId = request.getParameter("memberId");
                if (memberId == null) {
                    memberId = (String) request.getAttribute("memberId");
                }
                if (memberId != null) {
                    //刷新token
                    String token = request.getParameter("token");
                    if (StringUtil.isEmpty(token)) {
                        token = UUID.randomUUID().toString().replaceAll("-", "");
                        stringRedisTemplate.opsForValue().set(APPConfig.TOKEN_PREFIX + memberId, token, APPConfig.TOKEN_EXPIRE, TimeUnit.SECONDS);
                    }
                    appResult.setToken(token);

                }
                responseWrite(request,response, appResult);
                modelAndView.clear();
            }
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    /**
     * 日志记录
     *
     * @param request
     */
    public void log(HttpServletRequest request) {
        //生成请求ID
        String requestId = RandomUtil.createRandom(false,16);
        request.setAttribute("requestId",requestId);

        TreeMap<String, String[]> map = new TreeMap(request.getParameterMap());
        StringBuilder logStr = new StringBuilder();
        logStr.append("[---请求---]{").append("\r\n\t");
        logStr.append("requestId = " + requestId).append("\r\n\t");//请求ID
        logStr.append("IP = " + IPUtil.getClientIp(request)).append("\r\n\t"); //log请求IP
        logStr.append("URL = " + request.getRequestURL().toString()).append("\r\n\t"); //log请求URL
        logStr.append("params = " + new GsonBuilder().enableComplexMapKeySerialization().create().toJson(map)).append("\r\n\t");//log请求参数
        logStr.append("header = "+new GsonBuilder().enableComplexMapKeySerialization().create().toJson(getHeadersInfo(request))).append("\r\n");
        logStr.append("}");
        log.info(logStr.toString());
    }

    private Map<String, String> getHeadersInfo(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }

    /**
     * 请求验证
     *
     * @param request
     * @return
     */
    private APPResult requestVerify(HttpServletRequest request) {
        String uri = request.getRequestURI();

        //时间验证
        if (uri.indexOf("/app/time") > -1) {
            return null;
        }
        //磨盒数据
        if (uri.indexOf("/app/member/td/") > -1) {
            return null;
        }
        //版本验证
        if (uri.indexOf("/app/download") > -1) {
            return null;
        }
        //首页轮播
        if (uri.indexOf("/app/carousel") > -1) {
            return null;
        }
        String unix_time = request.getParameter("rtime");

        if (unix_time == null) {
            return new APPResult(APICode.API格式参数错误.getCode(), "请求参数错误");//格式错误
        }
        if (System.currentTimeMillis() / 1000 - Long.valueOf(unix_time) > APPConfig.REQUEST_TIME_EXPIRE) {
            return new APPResult(APICode.API格式参数错误.getCode(), "请求超时");//请求超时
        }


        //签名验证
        String sign = request.getHeader("sign");
        if (sign == null) {
            return new APPResult(APICode.API格式参数错误.getCode(), "请求参数错误");//格式错误
        }
        //签名验证
        if (!verifySign(request, sign)) {
            return new APPResult(APICode.API格式参数错误.getCode(), "请求参数错误");//签名错误
        }
        //ios 热更新
        if (uri.indexOf("/app/ios/") > -1) {
            String ios  = request.getParameter("ios");
            String version = request.getParameter("version");
            if (ios == null || version == null) {
                return new APPResult(APICode.API格式参数错误.getCode(), "请求参数错误");//格式错误
            }
        }
        //Token验证
        if (uri.indexOf("/app/member/") > -1) {
            String token = request.getParameter("token");
            String memberId = request.getParameter("memberId");
            if (token == null || memberId == null) {
                return new APPResult(APICode.API格式参数错误.getCode(), "账号已过期或者未登录");//格式错误
            }
            if (!stringRedisTemplate.hasKey(APPConfig.TOKEN_PREFIX + memberId) || !stringRedisTemplate.opsForValue().get(APPConfig.TOKEN_PREFIX + memberId).equals(token)) {
//                memberService.updatePushIdByMemberId(Integer.valueOf(memberId));//更新 pushId  为空
                return new APPResult(APICode.认证过期.getCode(), "账号已过期或者未登录");//认证过期
            }
        }
        return null;
    }


    /**
     * 签名验证
     *
     * @param request
     * @param sign
     * @return
     */
    private boolean verifySign(HttpServletRequest request, String sign) {
        TreeMap<String, String[]> map = new TreeMap(request.getParameterMap());

        StringBuilder sb = new StringBuilder();
        for (String s : map.keySet()) {
            sb.append(s);
            for (String s1 : map.get(s)) {
                sb.append(s1);
            }
        }
        sb.append(APPConfig.SIGN_KEY);
        String nowSign = MD5Util.md5(sb.toString());
        return nowSign.toLowerCase().equals(sign.toLowerCase());
    }


    /*
     * 返回json
     *
     * @param response
     * @param object
    */
    private void responseWrite(HttpServletRequest request, HttpServletResponse response, APPResult appResult) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        String appResultJson = appResult.toJson();
        StringBuilder logStr = new StringBuilder();
        logStr.append("[---返回---]{").append("\r\n\t");
        logStr.append("requestId = ").append(request.getAttribute("requestId")).append("\r\n\t");
        logStr.append("返回结果 = ").append(appResultJson).append("\r\n");
        try {
            out = response.getWriter();
            out.append(appResultJson);
        } catch (IOException e) {
            logStr.append("\t").append("报错 = ").append(e.getMessage()).append("\r\n");
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
            logStr.append("}");
            log.info(logStr.toString());
        }
    }
}
