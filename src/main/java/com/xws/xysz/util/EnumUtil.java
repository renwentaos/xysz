package com.xws.xysz.util;

import com.xws.xysz.enums.EnumInterface;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 枚举工具类
 * JokerYG
 * Date: 2018-11-22
 * Time: 10:48
 */
public class EnumUtil {

    /**
     * 枚举类转为map
     * @param enumValues 枚举类的values
     * @return Map<name,code>
     */
    public static Map<Object, Object> enumToMap(EnumInterface[] enumValues) {
        Map<Object, Object> map = new LinkedHashMap<>();
        for (EnumInterface value : enumValues) {
            map.put(value.getName(),value.getCode());
        }
        return map;
    }


    /**
     * 枚举类转为map
     * @param enumValues 枚举类的values
     * @return Map<code,name>
     */
    public static Map<Object, Object> enumToMapCode(EnumInterface[] enumValues) {
        Map<Object, Object> map = new LinkedHashMap<>();
        for (EnumInterface value : enumValues) {
            map.put(value.getCode(),value.getName());
        }
        return map;
    }
}
