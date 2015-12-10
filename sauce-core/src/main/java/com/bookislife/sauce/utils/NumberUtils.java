package com.bookislife.sauce.utils;

import java.text.DecimalFormat;

/**
 * Created by SidneyXu on 2015/12/10.
 */
public class NumberUtils {

    public static String halfToFull(String s) {
        StringBuffer sb = new StringBuffer(s);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= '0' && c <= '9') {
                sb.setCharAt(i, (char) (c - '0' + '０'));
            }
        }
        return sb.toString();
    }

    public static String addZero(Integer value, int size) {
        StringBuilder sb = new StringBuilder();

        if (value != null) {
            String result = String.valueOf(value);
            for (int i = 0; i < size - result.length(); i++) {
                sb.append("0");
            }
            sb.append(value);
        }
        return sb.toString();
    }

    public static String getMoneyStr(Integer money) {
        DecimalFormat format = new DecimalFormat("#,###");
        return format.format(money);
    }

    public static String removeMoneyComma(String str) {
        return str.replace(",", "");
    }


}
