package com.xws.xysz.util;

import com.xws.xysz.exception.ErrorException;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 杨光 on 2015/5/8.
 */
public class DispatcherUtil {

    /**
     * 提示并跳转
     * @param msg 提示信息
     * @param url 自动跳转的url
     * @param attributes 存储信息
     * @return 视图名称
     */
    public static String AlertAndRedirect(String msg, String url, RedirectAttributes attributes) {
        attributes.addFlashAttribute("msg", msg);
//        if(msg.equals(TipKey.MemberMsg.登录过期.getKey())){
//            attributes.addFlashAttribute("url", "/login");
//        }else {
            attributes.addFlashAttribute("url", url);
//        }
        return "redirect:/tips";
    }


    /**
     * 提示并跳转(request和response版)
     * @param msg 提示信息
     * @param url 自动跳转的url
     */
    public static void AlertAndRedirect(String msg, String url, HttpServletRequest request, HttpServletResponse response)  {
        FlashMap flashMap = new FlashMap();
        flashMap.put("msg", msg);
        flashMap.put("url", url);
        FlashMapManager flashMapManager = RequestContextUtils.getFlashMapManager(request);
        flashMapManager.saveOutputFlashMap(flashMap, request, response);
        try {
            response.sendRedirect("/tips");
        }catch (IOException e){
            throw new ErrorException("提示并跳转(request和response版)跳转时出错",e);
        }
    }

}
