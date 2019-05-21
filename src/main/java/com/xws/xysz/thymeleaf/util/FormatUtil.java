package com.xws.xysz.thymeleaf.util;

import java.text.DecimalFormat;

/**
 * JokerYG
 * Date: 2018-11-28
 * Time: 16:00
 */
public class FormatUtil {
    /**
     * format decimal method
     */
    public String formatDecimal(Double number, String pattern) {
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(number);
    }

    public String formatInteger(Integer number, String pattern) {
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(number);
    }


}
