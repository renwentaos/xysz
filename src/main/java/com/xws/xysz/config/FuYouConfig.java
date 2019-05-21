package com.xws.xysz.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 富友相关
 */
@Component
public class FuYouConfig {


//富友绑卡获取验证码地址
    public  static  String CODE_URL;
    @Value("${fy.code-url}")
    public void setCodeUrl(String codeUrl){
        CODE_URL= codeUrl;
    }
//绑卡地址
    public static String CARD_URL;
    @Value("${fy.card-url}")
    public void setCardUrl(String cardUrl){
        CARD_URL = cardUrl;
    }
//划扣地址
    public static String PAY_URL;
    @Value("${fy.pay-url}")
    public void setPayUrl(String payUrl){
        PAY_URL = payUrl;
    }
//商户号
    public static String FY_MCHNTCD;
    @Value("${fy.mchntcd}")
    public void setFyMchntcd(String fyMchntcd){FY_MCHNTCD = fyMchntcd;}
//商户协议绑卡秘钥
    public static String FY_KEY;
    @Value("${fy.key}")
    public void setFyKey(String fyKey){FY_KEY = fyKey;}
//商户代收付秘钥
    public static String FY_PAY_KEY;
    @Value("${fy.pay-key}")
    public void setFyPayKey(String fyPayKey){FY_PAY_KEY = fyPayKey;}
//商户代收付地址
    public static String FY_OTHER_PAY_URL;
    @Value("${fy.other-pay-url}")
    public void setFyOtherPayUrl(String fyOtherPayUrl){FY_OTHER_PAY_URL = fyOtherPayUrl;}
//#用于划扣测试
    public static String FY_USER_ID;
    @Value("${fy.user-id}")
    public void setFyUserId(String fyUserId){FY_USER_ID = fyUserId;}
//代收付商户号
public static String FY_PAY_MCHNTCD;
    @Value("${fy.pay-mchntcd}")
    public void setFyPayMchntcd(String fyPayMchntcd){FY_PAY_MCHNTCD = fyPayMchntcd;}


//    public static String BACK_URL;
//    @Value("${fy.back-url}")
//    public void setBackUrl(String backUrl){
//        BACK_URL = backUrl;
//    }

}
