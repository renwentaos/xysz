package com.xws.xysz.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * JokerYG
 * Date: 2018-11-21
 * Time: 15:50
 */
@Component
public class SystemConfig {


    //网站域名
    public static String SITE;
    @Value("${system.site}")
    public void setSITE(String site) {
        SITE = site;
    }

    //默认的分页每页显示数量
    public static int PAGE_SIZE = 5;

    //一个用户所属的商户数量上限
    public static int MEMBER_MERCHANT_TOPLIMIT = 3;

    //实名认证
    public static String ID98_URL = "http://api.id98.cn/api/idcard";
    public static String ID98_APPKEY = "195b593427ed82f6985a242cd29977c5";

    //短信有效时长
    public final static int INTERVAL_TIME = 3;


    //图片空间
    public static String IMG_SITE = "http://img.yunshuidi.cn/";//图片地址
    public static String IMG_BUCKET = "yunshuidi";//图片空间名
    public static String IMG_AK = "5p0iLQTBPzU8otkNcqLNkbCOqhg0j1xjzTQNs04J";//AccessKey
    public static String IMG_SK = "xLBBKpgvWKZjFVrliMWt7rmqEMfnjnRQyrsAZ7ah";//SecretKey

    /**
     * 短信相关
     */
    public final static String UID = "123001";
    public final static String PWD = "tkqwl123";

    //四要素验证
    public final static String V_HOST = "http://lundroid.market.alicloudapi.com";
    public final static String V_PATH = "/lianzhuo/verifi";
    public final static String V_METHOD = "GET";
    public final static String V_APPCODE = "02f8bb13aef6495bb1edd1255299d9a8";


    //极光appkey
    public static String PUSH_KEY;
//    @Value("${push.app-key}")
//    public void setPushKey(String pushKey) {
//        PUSH_KEY = pushKey;
//    }
    //极光验证key
    public static String MASTER_SECRET;
//    @Value("${push.master-secret}")
//    public void setMasterSecret(String masterSecret) {
//        MASTER_SECRET = masterSecret;
//    }
    //极光环境  判断 生产环境开发环境用
    public static Boolean IS_TEST;
//    @Value("${push.is-test}")
//    public void setIsTest(boolean isTest) {
//        IS_TEST = isTest;
//    }

}
