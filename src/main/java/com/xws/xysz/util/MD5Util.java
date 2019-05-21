package com.xws.xysz.util;

///
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MD5Util {
    public MD5Util() {
    }

    public static void main(String[] args) {
        String md5_1 = md5("123");
        String md5_2 = md5("111111");
        System.out.println(md5_1 + "\n" + md5_2);
        String sha_1 = sha("123456");
        String sha_2 = sha("d");
        System.out.println(sha_1 + "\n" + sha_2);
    }

    public static String md5AndSha(String inputText) {
        return sha(md5(inputText));
    }

    public static String md5(String inputText) {
        return inputText != null && !"".equals(inputText)?encrypt(inputText, "md5").toUpperCase():inputText.toUpperCase();
    }

    public static String sha(String inputText) {
        return inputText != null && !"".equals(inputText)?encrypt(inputText, "sha-1"):inputText;
    }

    private static String encrypt(String inputText, String algorithmName) {
        if(inputText != null && !"".equals(inputText)) {
            if(inputText != null && !"".equals(inputText.trim())) {
                if(algorithmName == null || "".equals(algorithmName.trim())) {
                    algorithmName = "md5";
                }

                Object encryptText = null;

                try {
                    MessageDigest m = MessageDigest.getInstance(algorithmName);
                    m.update(inputText.getBytes("UTF8"));
                    byte[] s = m.digest();
                    return hex(s);
                } catch (NoSuchAlgorithmException var5) {
                    var5.printStackTrace();
                } catch (UnsupportedEncodingException var6) {
                    var6.printStackTrace();
                }

                return (String)encryptText;
            } else {
                throw new IllegalArgumentException("请输入要加密的内容");
            }
        } else {
            return inputText;
        }
    }

    private static String hex(byte[] arr) {
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < arr.length; ++i) {
            sb.append(Integer.toHexString(arr[i] & 255 | 256).substring(1, 3));
        }

        return sb.toString();
    }

    /**
     * 富友代收付加密
     * @param origin
     * @param charsetname
     * @return
     */
    public static String encode(String origin, String charsetname){
        String resultString = null;
        resultString = new String(origin);
        MessageDigest md;
        try{
            md = MessageDigest.getInstance("MD5");
        }catch(NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }
        if(charsetname == null || "".equals(charsetname)){
            resultString = Hex.encodeHexString(md.digest(resultString.getBytes()));
        }else{
            try{
                resultString = Hex.encodeHexString(md.digest(resultString.getBytes(charsetname)));
            }catch(UnsupportedEncodingException e){
                throw new RuntimeException(e);
            }
        }
        return resultString;
    }
}
