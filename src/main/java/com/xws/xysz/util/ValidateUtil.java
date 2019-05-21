package com.xws.xysz.util;

import java.math.BigDecimal;

/**
 * 校验工具类
 * JokerYG
 * Date: 2018-11-28
 * Time: 11:32
 */
public class ValidateUtil {

    /**
     * 小于判断(Bigdecimal)
     *
     * @param validateValue 要验证的值
     * @param targetValue   目标值
     * @return true:validateValue 小于 targetValue ,false :validateValue 大于等于 targetValue 或者参数为空
     */
    public static boolean lt(BigDecimal validateValue, String targetValue) {
        if (validateValue != null && !StringUtil.isEmpty(targetValue)) {
            if (validateValue.compareTo(new BigDecimal(targetValue)) == -1) {
                return true;
            }
        }
        return false;
    }

    /**
     * 小于判断(Integer)
     *
     * @param validateValue 要验证的值
     * @param targetValue   目标值
     * @return true:validateValue 小于 targetValue ,false :validateValue 大于等于 targetValue 或者参数为空
     */
    public static boolean lt(Integer validateValue, String targetValue) {
        if (validateValue != null && !StringUtil.isEmpty(targetValue)) {
            if (validateValue < Integer.valueOf(targetValue)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 大于判断(Integer)
     *
     * @param validateValue 要验证的值
     * @param targetValue   目标值
     * @return true:validateValue 大于 targetValue ,false :validateValue 小于等于 targetValue 或者参数为空
     */
    public static boolean gt(Integer validateValue, String targetValue) {
        if (validateValue != null && !StringUtil.isEmpty(targetValue)) {
            if (validateValue > Integer.valueOf(targetValue)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 大于判断(Bigdecimal)
     *
     * @param validateValue 要验证的值
     * @param targetValue   目标值
     * @return true:validateValue 大于 targetValue ,false :validateValue 小于等于 targetValue 或者参数为空
     */
    public static boolean gt(BigDecimal validateValue, String targetValue) {
        if (validateValue != null && !StringUtil.isEmpty(targetValue)) {
            if (validateValue.compareTo(new BigDecimal(targetValue)) == 1) {
                return true;
            }
        }
        return false;
    }


    /**
     * 长度验证（不包含两侧空格）
     * @param strs 要验证的字符串
     * @param length 目标长度
     * @return 是否通过
     */
    public static boolean minLength(String strs,int length){
        if(strs == null  || strs.trim().length()<length){
            return false;
        }
        return true;
    }
}
