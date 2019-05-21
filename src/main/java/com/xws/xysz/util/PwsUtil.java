package com.xws.xysz.util;

import com.xws.xysz.config.APPConfig;

/**
 * @date 2017年7月27日 上午09:27:13
 * @annotation 密码加密解密
 */
public class PwsUtil {
    /**
     * 加密
     *
     * @param password
     * @return
     */
    public static String encrypt(String password) {
        byte[] enBytes = password.getBytes();
        byte[] enRsaArr;//RSA加密
        try {
            enRsaArr = RSAUtil.encryptByPublicKey(enBytes, APPConfig.RSA_PUB_KEY);
        } catch (Exception e) {
            return null;
        }
        password = new String(com.xws.xysz.util.Base64.encode(enRsaArr));//Base64编码
        return password;
    }

    /**
     * 解密
     *
     * @param password
     * @return
     */
    public static String dncrypt(String password) {
        char[] deChars = password.toCharArray();
        byte[] dnBytes = com.xws.xysz.util.Base64.decode(deChars);//解码
        try {
            password = new String(RSAUtil.decryptByPrivateKey(dnBytes, APPConfig.RSA_PRI_KEY));//解密
        } catch (Exception e) {
            return null;
        }
        return password;
    }

    /**
     * 加密
     *
     * @param password
     * @return
     */
    public static String encrypt(String password, String priKey) {
        byte[] enBytes = password.getBytes();
        byte[] enRsaArr;//RSA加密
        try {
            enRsaArr = RSAUtil.encryptByPrivateKey(enBytes, priKey);
        } catch (Exception e) {
            return null;
        }
        password = new String(com.xws.xysz.util.Base64.encode(enRsaArr));//Base64编码
        return password;
    }

    /**
     * 解密
     *
     * @param password
     * @return
     */
    public static String dncrypt(String password, String publicKey) {
        char[] deChars = password.toCharArray();
        byte[] dnBytes = com.xws.xysz.util.Base64.decode(deChars);//解码
        try {
            password = new String(RSAUtil.decryptByPublicKey(dnBytes, publicKey));//解密
        } catch (Exception e) {
            return null;
        }
        return password;
    }
}
