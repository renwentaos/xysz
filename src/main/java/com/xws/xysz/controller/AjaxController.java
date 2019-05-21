package com.xws.xysz.controller;



import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;


/**
 * @description: 验证码
 * @Author rwt
 * @Date 2018/11/27 09:36
 **/
@Slf4j
@Controller
public class AjaxController {



    @Autowired
    HttpServletRequest request;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
//    @Autowired
//    SendService sendService;
//
//
//
//    /**
//     * 发送短信验证码
//     *
//     * @param mobile
//     * @param codeType
//     * @return
//     */
//    @RequestMapping(value = "/sms", method = RequestMethod.GET)
//    public void sendSMSCaptcha(String mobile, String codeType, Model model) {
//        APPResult appResult = new APPResult();
//        //得到实际的短信验证码
//        String realSMSCaptcha = stringRedisTemplate.opsForValue().get(mobile + "smsCode" + codeType);
//        try {
//            String smsCaptcha = sendService.sendSMS(mobile, codeType, realSMSCaptcha);
//            stringRedisTemplate.opsForValue().set(mobile + "smsCode" + codeType,smsCaptcha, 10 * 60);
//            appResult.setResponseCode(APICode.成功.getCode());
//        } catch (WarnException e) {
//            appResult.setResponseCode(APICode.业务参数错误.getCode());
//            appResult.setErrorMsg(e.getMessage());
//        }finally {
//            model.addAttribute(appResult);
//        }
//    }
//
//    /**
//     * 生成图片验证码
//     * 图片的验证码文字存储在session中的imgCode
//     *
//     * @param h 高度
//     * @param w 宽度
//     * @param
//     */
//    @RequestMapping(value = "/imgcode", method = RequestMethod.GET)
//    public void imgCode(int h, int w, HttpServletResponse response) {
//        try {
//            ImgCode imgCode = new ImgCode(w, h, 5);
//            BufferedImage buffImg = imgCode.getImg(request);
//            // 禁止图像缓存。
//            response.setHeader("Pragma", "no-cache");
//            response.setHeader("Cache-Control", "no-cache");
//            response.setDateHeader("Expires", 0);
//
//            response.setContentType("image/jpeg");
//
//            // 将图像输出到Servlet输出流中。
//            ServletOutputStream sos = response.getOutputStream();
//            ImageIO.write(buffImg, "jpeg", sos);
//            response.flushBuffer();
//            sos.flush();
//            sos.close();
//        } catch (Exception e) {
//            log.error("生成验证码错误", e);
//        }
//    }
}
