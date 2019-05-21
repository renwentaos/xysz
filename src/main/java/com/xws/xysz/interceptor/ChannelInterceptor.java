package com.xws.xysz.interceptor;


import com.xws.xysz.util.CookieUtil;
import com.xws.xysz.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * JokerYG
 * Date: 2019-02-13
 * Time: 14:55
 */
public class ChannelInterceptor implements HandlerInterceptor {
//    @Autowired
//    ChannelReportService channelReportService;

//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        if (handler instanceof ResourceHttpRequestHandler) {
//            //非controller里的直接跳转
//            return true;
//        } else {
//            String channelId = request.getParameter("channelId");
//            if(StringUtil.isPositiveInteger(channelId)) {
//
//                String uv = CookieUtil.getCookie(request, "uv");
//                //不存在则存入cookie
//                if (StringUtil.isEmpty(uv)) {
//                    //存入数据库
////                    channelReportService.addUV(Integer.valueOf(channelId));
//
//                    //计算离明天0点所需要的秒数
//                    Calendar calendar = Calendar.getInstance();
//                    long nowTime = calendar.getTime().getTime();
//                    calendar.add(GregorianCalendar.DATE, 1);
//                    calendar.set(Calendar.HOUR_OF_DAY, 0);
//                    calendar.set(Calendar.MINUTE, 0);
//                    calendar.set(Calendar.SECOND, 0);
//                    calendar.set(Calendar.MILLISECOND, 0);
//                    long targetTime = calendar.getTime().getTime();
//                    int cookieAge = (int) (targetTime - nowTime) / 1000;
//
//                    //存入cookie
//                    CookieUtil.addCookie("uv", "1", cookieAge, response);
//                }
//            }
//            return true;
//        }
//    }
}
