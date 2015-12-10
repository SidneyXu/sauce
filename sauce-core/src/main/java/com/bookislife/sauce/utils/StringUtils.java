package com.bookislife.sauce.utils;

/**
 * Created by SidneyXu on 2015/12/10.
 */
public class StringUtils {

    public static String lTrim(String str) {
        if (str == null)
            return str;
        return str.replaceAll("^[　\\s]*", "");
    }

    public static String rTrim(String str) {
        if (str == null)
            return str;
        return str.replaceAll("[　\\s]*$", "");
    }

    public static String trim(String str) {
        if (str == null)
            return str;
        return str.replaceAll("[　\\s]*", "");
    }

    public static String slice(String str, int beginIndex, int endIndex) {
        if (str == null)
            return str;
        int len = str.length();
        beginIndex = beginIndex < 0 ? len + beginIndex : beginIndex;
        endIndex = endIndex < 0 ? len + endIndex : endIndex;
        if (beginIndex > endIndex) {
            return str;
        }
        return str.substring(beginIndex, endIndex);
    }
}
