package com.xws.xysz.util;


import com.qiniu.common.Zone;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;
import com.xws.xysz.config.SystemConfig;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 七牛
 * Created by yangg_000 on 2015/8/3.
 */
public class QNUtil {


    private static Auth auth = Auth.create(SystemConfig.IMG_AK, SystemConfig.IMG_SK);//初始化

    public static Auth getAuth() {
        return auth;
    }

    /**
     * 简单上传，使用默认策略
     *
     * @return
     */
    public static String getUpToken() {
        return auth.uploadToken(SystemConfig.IMG_BUCKET);
    }

    /**
     * 覆盖上传
     *
     * @return
     */
    public static String getCoverUpToken(String key) {
        return auth.uploadToken(SystemConfig.IMG_BUCKET, key);
    }

    /**
     * 带303跳转的token
     *
     * @param returnUrl
     * @return
     */
    public static String getUpTokenAndReturnURL(String returnUrl) {
        //SystemConfig.SITE
        return auth.uploadToken(SystemConfig.IMG_BUCKET, null, 3600L, new StringMap()
                .put("returnUrl", returnUrl));
    }



}