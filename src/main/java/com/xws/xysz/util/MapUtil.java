package com.xws.xysz.util;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Map<K,V>工具类
 */
public class MapUtil {

    /**
     * 根据key返回map的string值（key不存在直接返回null）
     *
     * @return 对应key的结果string，key不存在返回null
     */
    public static String getStrByKey(Object key, Map map) {
        if (map != null && map.get(key) != null) {
            return map.get(key).toString();
        }
        return null;
    }


    /**
     * 根据key返回map的Integer值（key不存在直接返回null）,如果是小数则直接舍去小数部分
     *
     * @return 对应key的结果Integer，key不存在返回null
     */
    public static Integer getIntByKey(Object key, Map map) {
        if (map != null && map.get(key) != null) {
            String val = map.get(key).toString();
            if (StringUtil.isWholeNumber(val)) {
                return Integer.valueOf(val);
            } else if (StringUtil.isDecimal(val)) {
                Double f = Double.valueOf(val);

                return (int) Math.floor(f);
            }

        }
        return null;
    }


    /**
     * 根据key返回map的BigDecimal值（key不存在直接返回null）
     *
     * @return 对应key的结果BigDecimal，key不存在返回null
     */
    public static BigDecimal getDecimalByKey(Object key, Map map) {
        if (map != null && map.get(key) != null) {
            String val = map.get(key).toString();
            if (StringUtil.isDecimal(val)||StringUtil.isWholeNumber(val)) {
                return new BigDecimal(val);
            }
        }
        return null;
    }
}
