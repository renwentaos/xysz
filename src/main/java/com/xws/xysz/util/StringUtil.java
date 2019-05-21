package com.xws.xysz.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.helpers.MessageFormatter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * Created by 光 on 2015/3/30.
 */
public class StringUtil {

    /****************************************************************************************************
     * @author 潘要东
     * @create 2018/4/24 11:59
     * @descriptions <p>字符串内容替换</p>
     * @params [format, arg]
     * @return java.lang.String
     ****************************************************************************************************/
    public static String format(String s, Object arg) {
        return MessageFormatter.format(s, arg).getMessage();
    }

    /****************************************************************************************************
     * @author 潘要东
     * @create 2018/4/24 11:59
     * @descriptions <p>字符串内容替换</p>
     * @params [format, arg1, arg2]
     * @return java.lang.String
     ****************************************************************************************************/
    public static String format(String s, Object arg1, Object arg2) {
        return MessageFormatter.format(s, arg1, arg2).getMessage();
    }

    /****************************************************************************************************
     * @author 潘要东
     * @create 2018/4/24 11:59
     * @descriptions <p>字符串内容替换</p>
     * @params [format, arg1, arg2, arg3]
     * @return java.lang.String
     ****************************************************************************************************/
    public static String format(String s, Object arg1, Object arg2, Object arg3) {
        return MessageFormatter.arrayFormat(s, new Object[]{arg1, arg2, arg3}).getMessage();
    }

    /****************************************************************************************************
     * @author 潘要东
     * @create 2018/4/24 11:59
     * @descriptions <p>字符串内容替换</p>
     * @params [format, args]
     * @return java.lang.String
     ****************************************************************************************************/
    public static String format(String s, Object[] args) {
        return MessageFormatter.arrayFormat(s, args).getMessage();
    }


    private static boolean isMatch(String regex, String orginal){
        if (orginal == null || orginal.trim().equals("")) {
            return false;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher isNum = pattern.matcher(orginal);
        return isNum.matches();
    }

    /**
     * 是否是正整数
     * @param orginal
     * @return
     */
    public static boolean isPositiveInteger(String orginal) {
        return isMatch("^\\+{0,1}[1-9]\\d*", orginal);
    }

    /**
     * 是否是负整数
     * @param orginal
     * @return
     */
    public static boolean isNegativeInteger(String orginal) {
        return isMatch("^-[1-9]\\d*", orginal);
    }

    /**
     * 是否是整数
     * @param orginal
     * @return
     */
    public static boolean isWholeNumber(String orginal) {
        return isMatch("[+-]{0,1}0", orginal) || isPositiveInteger(orginal) || isNegativeInteger(orginal);
    }

    /**
     * 是否是正小数
     * @param orginal
     * @return
     */
    public static boolean isPositiveDecimal(String orginal){
        return isMatch("\\+{0,1}[0]\\.[1-9]*|\\+{0,1}[1-9]\\d*\\.\\d*", orginal);
    }

    /**
     * 是否是负小数
     * @param orginal
     * @return
     */
    public static boolean isNegativeDecimal(String orginal){
        return isMatch("^-[0]\\.[1-9]*|^-[1-9]\\d*\\.\\d*", orginal);
    }

    /**
     * 是否是小数
     * @param orginal
     * @return
     */
    public static boolean isDecimal(String orginal){
        return isMatch("[-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+", orginal);
    }

    /**
     * 删除input字符串中的html格式
     *
     * @param input
     * @param length
     * @return
     */
    public static String splitAndFilterString(String input, int length) {
        if (input == null || input.trim().equals("")) {
            return "";
        }
        // 去掉所有html元素,
        String str = input.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll(
                "<[^>]*>", "");
        str = str.replaceAll("[(/>)<]", "");
        int len = str.length();
        if (len <= length) {
            return str;
        } else {
            str = str.substring(0, length);
            str += "......";
        }
        return str;
    }

    /**
     * 判断字符串是否为null或者空字符串(去除空格)
     * @param str
     * @return true:为空  false:不为空
     */
    public static boolean isEmpty(String str){
        return str == null || str.trim().length() == 0;
    }

    /**
     * 判断多个字符串是否为null或者空字符串(去除空格)，可分辨and和or关系
     * @param isAnd 多个字符串是否为空的判断条件的关系，true:and，false:or
     * @param strs 字符串列表
     * @return true:符合条件  false:不符合条件
     */
    public static boolean isEmpty(boolean isAnd,String...strs){
        if(isAnd) {
            for (String str : strs) {
                if(!isEmpty(str)){
                    return false;
                }
            }
            return true;
        }else{
            //or 关系
            for (String str : strs) {
                if(isEmpty(str)){
                    return true;
                }
            }
            return false;
        }
    }


    /**
     * 使用java正则表达式去掉多余的.与0
     * @param s
     * @return
     */
    public static String subZeroAndDot(String s){
        if(s.indexOf(".") > 0){
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }


    /**
     * 去除两边空格,自动判断是否为空
     */
    public static String trim(String str){
        if(str!=null){
            str = str.trim();
        }
        return str;
    }

    /**
     * 数据脱敏（指定保留前后各N位，其他全加*）
     * @param str 原始数据
     * @param index 前边保留位数
     * @param end 结尾保留位数
     * @param size 加密星号的个数，如果为0则保持原位数替换
     * @return
     */
    public static String desensitize(String str,int index,int end,int size) {
        if (isEmpty(str)) {
            return null;
        }
        if(size == 0){
            size = str.length() - index;
            if(size < 0){
                size = 0;
            }
        }else{
            size = size+end;
        }


        return StringUtils.left(str, index).concat(StringUtils.leftPad(StringUtils.right(str, end), size, "*"));

    }
}
