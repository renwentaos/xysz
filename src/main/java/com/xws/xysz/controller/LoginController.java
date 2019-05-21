package com.xws.xysz.controller;


import com.xws.xysz.exception.WarnException;
import com.xws.xysz.model.Manager;
import com.xws.xysz.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;


/**
 * JokerYG
 * Date: 2018-11-19
 * Time: 11:25
 */
@Controller
public class LoginController {
    @Autowired
    HttpServletRequest request;

    @Autowired
    ManagerService managerService;
//
//    @Autowired
//    MerchantService merchantService;
//
//    @Autowired
//    ChannelService channelService;
//
//    /**
//     * 管理员登录页
//     */

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String aLogin() {
        //已登录则直接跳转默认页面
        Object session = request.getSession().getAttribute("adminInfo");
        if (session != null) {
            return "redirect:" + ((Manager) session).getDefaultPage();
        }
        return "adminLogin";
    }

    @RequestMapping(value = "/alogin", method = RequestMethod.GET)
    public String adminLogin() {
        //已登录则直接跳转默认页面
        Object session = request.getSession().getAttribute("adminInfo");
        if (session != null) {
            return "redirect:" + ((Manager) session).getDefaultPage();
        }
        return "adminLogin";
    }
//
//
    /**
     * 管理员登录
     */
    @RequestMapping(value = "/alogin", method = RequestMethod.POST)
    public String adminLogin(String userName, String password, String code, Model model) {
        //获得登录次数
        int count = 0;
        if (request.getSession().getAttribute("adminCount") != null) {
            count = Integer.parseInt(request.getSession().getAttribute("adminCount").toString());
        }
        try {
            //管理员登录
            Manager manager = managerService.login(userName, password, code, count, request.getSession().getAttribute("imgCode"));
            //登录成功
            //清除登录次数
            request.getSession().removeAttribute("adminCount");
            //持久化管理员信息
            request.getSession().setAttribute("adminInfo", manager);

            //跳转
            return "redirect:" + manager.getDefaultPage();
        } catch (WarnException e) {
            //登录失败
            //登录次数加1
            count++;
            request.getSession().setAttribute("adminCount", count);
            //登录失败信息
            model.addAttribute("msg", e.getMessage());
            return "adminLogin";
        }
    }
//
//    /**
//     * 商户登录页
//     */
//    @RequestMapping(value = {"/mlogin","/fmlogin"}, method = RequestMethod.GET)
//    public String merchantLogin() {
//        //已登录则直接跳转默认页面
//        Object session = request.getSession().getAttribute("merchantInfo");
//        if (session != null) {
//            return "redirect:/merchant/index";
//        }
//        return "merchantLogin";
//    }
//
//    /**
//     * 商户登录
//     */
//    @RequestMapping(value = {"/mlogin","/fmlogin"}, method = RequestMethod.POST)
//    public String merchantLogin(String userName, String password, String code, Model model) {
//
//        //获得登录次数
//        int count = 0;
//        if (request.getSession().getAttribute("merchantCount") != null) {
//            count = Integer.parseInt(request.getSession().getAttribute("merchantCount").toString());
//        }
//        try {
//            //商户登录
//            Merchant manager = merchantService.login(userName, password, code, count, request.getSession().getAttribute("imgCode"));
//            //登录成功清除登录次数
//            request.getSession().removeAttribute("merchantCount");
//            //持久化商户信息
//            request.getSession().setAttribute("merchantInfo", manager);
//
//
//            //是否是虚假模式
//            if(request.getRequestURI().indexOf("/fmlogin") == 0 && manager.getLevel() == LevelEnum.Merchant.超级VIP商户.getCode()){
//                request.getSession().setAttribute("fake",1);
//            }else{
//                request.getSession().removeAttribute("fake");
//            }
//
//            //跳转
//            return "redirect:/merchant/index";
//        } catch (WarnException e) {
//            //登录失败
//            //登录次数加1
//            count++;
//            request.getSession().setAttribute("merchantCount", count);
//            //登录失败信息
//            model.addAttribute("msg", e.getMessage());
//            return "merchantLogin";
//        }
//    }
//
//
//
//    /**
//     * 渠道商登录页
//     */
//    @RequestMapping(value = "/clogin", method = RequestMethod.GET)
//    public String channelLogin() {
//        //已登录则直接跳转默认页面
//        Object session = request.getSession().getAttribute("channelInfo");
//        if (session != null) {
//            return "redirect:/channel/index";
//        }
//        return "channelLogin";
//    }
//
//    /**
//     * 渠道商登录
//     */
//    @RequestMapping(value = "/clogin", method = RequestMethod.POST)
//    public String channelLogin(String userName, String password, String code, Model model) {
//        //获得登录次数
//        int count = 0;
//        if (request.getSession().getAttribute("channelCount") != null) {
//            count = Integer.parseInt(request.getSession().getAttribute("channelCount").toString());
//        }
//        try {
//            //渠道商登录
//            Channel channel = channelService.login(userName, password, code, count, request.getSession().getAttribute("imgCode"));
//            //登录成功清除登录次数
//            request.getSession().removeAttribute("channelCount");
//            //持久化商户信息
//            request.getSession().setAttribute("channelInfo", channel);
//
//            //跳转
//            return "redirect:/channel/index";
//        } catch (WarnException e) {
//            //登录失败
//            //登录次数加1
//            count++;
//            request.getSession().setAttribute("channelCount", count);
//            //登录失败信息
//            model.addAttribute("msg", e.getMessage());
//            return "channelLogin";
//        }
//    }
}
