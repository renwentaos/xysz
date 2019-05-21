package com.xws.xysz.controller.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * JokerYG
 * Date: 2018-11-19
 * Time: 9:24
 */
@Controller("appIndex")
@RequestMapping("/app")
public class IndexController {


//    @Autowired
//    MemberService memberService;
//    @Autowired
//    HttpServletRequest request;
//    @Autowired
//    StringRedisTemplate stringRedisTemplate;
//    @Autowired
//    SendService sendService;
//    @Autowired
//    AppVersionService appVersionService;
//    @Autowired
//    CarouselService carouselService;
//
//    /**
//     * 获取当前系统时间（UNIX时间戳，秒）
//     *
//     * @return 当前时间（UNIX时间戳，秒）
//     */
//    @RequestMapping(value = "/time")
//    public void getNowTime(Model model) {
//        APPResult appResult = new APPResult();
//        appResult.setResponseCode(APICode.成功.getCode());
//        appResult.setResult(System.currentTimeMillis() / 1000);
//        model.addAttribute(appResult);
//    }
//
//    /**
//     * 发送短信验证码
//     *
//     * @param mobile
//     * @param codeType
//     * @return
//     */
//    @RequestMapping(value = "/send/sms", method = RequestMethod.POST)
//    public void sendSMSCaptcha(String mobile, String codeType, Model model) {
//        APPResult appResult = new APPResult();
//        //得到实际的短信验证码
//        String realSMSCaptcha = stringRedisTemplate.opsForValue().get(mobile + "smsCode" + codeType);
//        try {
//            String smsCaptcha = sendService.sendSMS(mobile, codeType, realSMSCaptcha);
//            stringRedisTemplate.opsForValue().set(mobile + "smsCode" + codeType, smsCaptcha, 10 * 60, TimeUnit.SECONDS);
//            appResult.setResponseCode(APICode.成功.getCode());
//        } catch (WarnException e) {
//            appResult.setResponseCode(APICode.业务参数错误.getCode());
//            appResult.setErrorMsg(e.getMessage());
//        } finally {
//            model.addAttribute(appResult);
//        }
//    }
//
//    /**
//     * 会员注册账户
//     *
//     * @param mobile          手机号
//     * @param password        密码
//     * @param password1       确认密码
//     * @param code            验证码
//     * @param inviterMember   邀请人
//     * @param inviterMerchant 邀请人商户
//     * @param model
//     */
//    @RequestMapping(value = "/register", method = RequestMethod.POST)
//    public void register(String mobile, String password, String password1, String code, String inviterMember, String inviterMerchant,
//                         String sourceCode, String lat, String lng, Model model) {
//        APPResult appResult = new APPResult();
//        try {
//            Member member = memberService.register(mobile, PwsUtil.dncrypt(password), PwsUtil.dncrypt(password1), code,
//                    inviterMember, inviterMerchant, sourceCode, request.getRemoteAddr(), lat, lng, null);
//            appResult.setResult(member);
//            appResult.setResponseCode(APICode.成功.getCode());
//        } catch (WarnException e) {
//            appResult.setResponseCode(APICode.业务参数错误.getCode());
//            appResult.setErrorMsg(e.getMessage());
//        } finally {
//            model.addAttribute(appResult);
//        }
//    }
//
//    /**
//     * 登录
//     * @param mobile
//     * @param password
//     * @param model
//     */
//
//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public void login(String mobile, String password,String pushId, Model model) {
//        APPResult appResult = new APPResult();
//        try {
//            Member member = memberService.login(mobile, PwsUtil.dncrypt(password),pushId);
//            appResult.setResponseCode(APICode.成功.getCode());
//            appResult.setResult(member);
//            request.setAttribute("memberId", member.getId().toString());
//        } catch (WarnException e) {
//            //登录失败次数增加
//            appResult.setResponseCode(APICode.业务参数错误.getCode());
//            appResult.setErrorMsg(e.getMessage());
//        } finally {
//            model.addAttribute(appResult);
//        }
//    }
//
//    /**
//     * 忘记密码
//     *
//     * @param mobile
//     * @param code
//     * @param password
//     * @param model
//     */
//    @RequestMapping(value = "/forgot/pwd", method = RequestMethod.POST)
//    public void forgotPwd(String mobile, String code, String password, Model model) {
//        APPResult appResult = new APPResult();
//        try {
//            memberService.updateLoginPwd(mobile, code, PwsUtil.dncrypt(password));
//            appResult.setResponseCode(APICode.成功.getCode());
//        } catch (WarnException e) {
//            appResult.setResponseCode(APICode.业务参数错误.getCode());
//            appResult.setErrorMsg(e.getMessage());
//        } finally {
//            model.addAttribute(appResult);
//        }
//    }
//
//
//    /**
//     * Android 版本更新
//     *
//     * @param model
//     */
//    @RequestMapping(value = "/download", method = RequestMethod.POST)
//    public void downLoad(Model model) {
//        APPResult appResult = new APPResult();
//        Map<String, String> map = new HashMap<>();
//        AppVersion appVersion = appVersionService.searchVersion(null,0);
//        if (appVersion != null && !appVersion.equals("")) {
//            map.put("version", appVersion.getVersion());
//            map.put("url", appVersion.getUrl());
//        }
//        appResult.setResponseCode(APICode.成功.getCode());
//        appResult.setResult(map);
//        model.addAttribute(appResult);
//    }
//
//    /**
//     *ios 热更新
//     * @param version
//     * @param ios
//     * @param model
//     */
//    @RequestMapping(value = "/ios/update", method = RequestMethod.POST)
//    public void downLoad(String version, String ios, Model model) {
//        APPResult appResult = new APPResult();
//        Map<String, String> map = new HashMap<>();
//        AppVersion appVersion = appVersionService.searchVersion(version,1);
//        if (appVersion != null && !appVersion.equals("")) {
//            map.put("version", appVersion.getVersion());
//            map.put("js", appVersion.getUrl());
//        }
//        appResult.setResponseCode(APICode.成功.getCode());
//        appResult.setResult(map);
//        model.addAttribute(appResult);
//    }
//
//    /**
//     *获取轮播数据
//     * @param model
//     */
//    @RequestMapping(value = "/carousel", method = RequestMethod.POST)
//    public void carousel(Model model) {
//        APPResult appResult = new APPResult();
//        List<Carousel> list = carouselService.getAllList();
//        appResult.setResponseCode(APICode.成功.getCode());
//        appResult.setResult(list);
//        model.addAttribute(appResult);
//    }
}
