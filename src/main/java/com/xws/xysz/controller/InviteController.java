package com.xws.xysz.controller;



import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@Controller("merchantInvite")
@RequestMapping("/invite")
public class InviteController {

//    @Autowired
//    ProjectService projectService;
//    @Autowired
//    StringRedisTemplate stringRedisTemplate;
//    @Autowired
//    SendService sendService;
//    @Autowired
//    MemberService memberService;
//    @Autowired
//    HttpServletRequest request;
//    @Autowired
//    ChannelService channelService;
//
//
//    /**
//     * 商户邀请 渠道商邀请
//     * @param merchantId
//     * @param channelId
//     * @param model
//     * @return
//     */
//    @RequestMapping(value = "/index", method = RequestMethod.GET)
//    public String index(String merchantId,String channelId, Model model) {
//        List<Project> list = new ArrayList<>();
//        if(StringUtils.isNotBlank(merchantId) && StringUtils.isNotBlank(channelId)){
//            model.addAttribute("msg","参数错误");
//        }else if(StringUtils.isBlank(merchantId) && StringUtils.isBlank(channelId)){
//            list = projectService.getProjectListByVip();
//        }else{
//            if(StringUtils.isNotBlank(merchantId)){
//                request.getSession().setAttribute("inviteMerchantId",merchantId);
//                list = projectService.getProjectListByMerchantId(merchantId);
//            }
//            if(StringUtils.isNotBlank(channelId)){
//                try{
//                    channelService.selectByChannelId(channelId);
//                    request.getSession().setAttribute("channelId",channelId);
//                    list = projectService.getNewOneProject();
//                }catch (WarnException e){
//                    model.addAttribute("msg",e.getMessage());
//                }
//            }
//        }
//        model.addAttribute("list",list);
//        return "invite/index";
//    }
//
//    /**
//     * 发送短信验证码
//     *
//     * @param mobile
//     * @param codeType
//     * @return
//     */
//    @RequestMapping(value = "/sms", method = RequestMethod.POST,produces = "application/json;charset=utf-8")
//    @ResponseBody
//    public String sendSMSCaptcha(String mobile, String codeType) {
//                 //得到实际的短信验证码
//        try {
//            String realSMSCaptcha = stringRedisTemplate.opsForValue().get(mobile + "smsCode" + codeType);
//            String smsCaptcha = sendService.sendSMS(mobile, codeType, realSMSCaptcha);
//            stringRedisTemplate.opsForValue().set(mobile + "smsCode" + codeType,smsCaptcha, 10 * 60, TimeUnit.SECONDS);
//            return String.valueOf(180);
//        } catch (WarnException e) {
//            return e.getMessage();
//        }
//    }
//
//    /**
//     * 验证手机号手否存在
//     * @param mobile
//     * @return true 已存在 false 不存在
//     */
//    @RequestMapping(value = "/validate/mobile", method = RequestMethod.POST)
//    @ResponseBody
//    public boolean mobileValidate(String mobile) {
//        return memberService.getMemberByMobile(mobile);
//    }
//
//
//    /**
//     *进入注册页
//     */
//    @RequestMapping(value = "/registers", method = RequestMethod.GET)
//    @Token(method = TokenMethod.create)
//    public String register(String merchantId, String channelId, String msg, Model model){
//        if(StringUtils.isNotBlank(msg)){
//            model.addAttribute("msg",msg);
//        }
//        model.addAttribute("merchantId",merchantId);
//        model.addAttribute("channelId",channelId);
//        return "invite/register";
//    }
//
//    /**
//     * 商户邀请渠道商邀请
//     * @param mobile
//     * @param code
//     * @param password
//     * @return
//     */
//    @RequestMapping(value = "/registers",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
//    @Token(method = TokenMethod.verify)
//    public String register(String mobile, String code, String password, String merchantId, String channelId, RedirectAttributes model){
//        try {
//            if(StringUtils.isNotBlank(merchantId) && StringUtils.isNotBlank(channelId)){
//                throw new WarnException("数据错误");
//            }
//            if(request.getSession().getAttribute("inviteMerchantId") != null && request.getSession().getAttribute("channelId") != null){
//                throw new WarnException("数据错误");
//            }else if(request.getSession().getAttribute("inviteMerchantId") == null && request.getSession().getAttribute("channelId") == null ){
//                    if(StringUtils.isNotBlank(merchantId)){
//                        memberService.register(mobile,password,password,code,null,merchantId,"1001", MapsUtils.getIpAddress(request),null,null,null);
//                    }else if(StringUtils.isNotBlank(channelId)){
//                        channelService.selectByChannelId(channelId);
//                        memberService.register(mobile,password,password,code,null,null,"1001", MapsUtils.getIpAddress(request),null,null,channelId);
//                    }else{
//                        memberService.register(mobile,password,password,code,null,null,"1001", MapsUtils.getIpAddress(request),null,null,null);
//                    }
//            }else if(request.getSession().getAttribute("inviteMerchantId") != null && request.getSession().getAttribute("channelId") == null){
//                String newMerchantId = String.valueOf(request.getSession().getAttribute("inviteMerchantId"));
//                memberService.register(mobile,password,password,code,null,newMerchantId,"1001", MapsUtils.getIpAddress(request),null,null,null);
//                request.getSession().removeAttribute("inviteMerchantId");
//            }else if(request.getSession().getAttribute("inviteMerchantId") == null && request.getSession().getAttribute("channelId") != null){
//                String newChannelId = String.valueOf(request.getSession().getAttribute("channelId"));
//                memberService.register(mobile,password,password,code,null,null,"1001", MapsUtils.getIpAddress(request),null,null,newChannelId);
//                request.getSession().removeAttribute("channelId");
//            }
//        }catch (WarnException e){
//            model.addFlashAttribute("msg",e.getMessage());
//            return "redirect:/invite/registers?merchantId="+merchantId+"&channelId="+channelId;
//        }
//        return "redirect:/html/download/download.html";
//    }
}
