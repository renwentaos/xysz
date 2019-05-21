package com.xws.xysz.interceptor;



import com.xws.xysz.annotations.Token;
import com.xws.xysz.enums.TokenMethod;
import com.xws.xysz.util.DispatcherUtil;
import com.xws.xysz.util.IdWorker;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

import static com.xws.xysz.enums.TokenMethod.create;

/**
 * Token拦截器
 * Created by 杨光 on 2017/6/22.
 */
public class TokenInterceptor extends HandlerInterceptorAdapter {
    final static String PC_TOKEN_PREFIX = "ptk";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            Token annotation = method.getAnnotation(Token.class);
            if (annotation != null) {
                switch (annotation.method()) {
                    case create:
                        createToken(request);
                        break;
                    case verify:
                        if (isRepeatSubmit(request)) {
                            DispatcherUtil.AlertAndRedirect("请不要重复提交", request.getHeader("Referer"), request, response);
                            return false;
                        }
                        break;
                    default:
                        break;
                }
            }
        }
        return super.preHandle(request, response, handler);
    }

    private void createToken(HttpServletRequest request) {
        IdWorker idWorker = IdWorker.getInstance();
        String token = PC_TOKEN_PREFIX + idWorker.nextId();
        request.getSession().setAttribute(token, "0");
//        redisService.set(token,"0",15*60);
        request.setAttribute("token", token);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //如果MODE有MSG变量并且此次操作是验证，则表示此次操作失败，重新生成token
        if (modelAndView != null && modelAndView.getViewName() != null && modelAndView.getModel() != null && modelAndView.getModel().containsKey("msg")) {
            if (handler instanceof HandlerMethod) {
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                Method method = handlerMethod.getMethod();
                Token annotation = method.getAnnotation(Token.class);
                if (annotation != null && annotation.method() == TokenMethod.verify) {
                    createToken(request);
                }
            }
        }
        super.postHandle(request, response, handler, modelAndView);
    }

    //重复提交校验
    private boolean isRepeatSubmit(HttpServletRequest request) {
        String clinetToken = request.getParameter("token");
        if (clinetToken == null) {
            return true;
        }
        if (request.getSession().getAttribute(clinetToken) == null) {
            return true;
        }
        request.getSession().removeAttribute(clinetToken);
//        Long delCount = redisService.del(clinetToken);
//        if (delCount == 0) {
//            return true;
//        }
        return false;
    }
}
