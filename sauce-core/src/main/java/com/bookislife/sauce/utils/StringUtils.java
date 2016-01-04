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

    public static String bytesToHex(byte[] signatureHash) {
        final char[] hexArray = {'0', '1', '2', '3', '4', '5', '6', '7',
                '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] hexChars = new char[signatureHash.length * 2];
        int v;
        for (int j = 0; j < signatureHash.length; j++) {
            v = signatureHash[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static String fillWith(String str, int size,
                                  char filled) {
        String tmp = str;
        if (tmp.length() >= size) {
            return str;
        }
        while (tmp.length() < size) {
            tmp = filled + tmp;
        }
        return str;
    }

    public static String fillWith(int num, int size,
                                  char filled) {
        return fillWith("" + num, size, filled);
    }

    public static char[] randomChars(int length) {
        char[] arr = new char[length];
        int count = 0;
        Outer:
        while (count < length) {
            char c = (char) (Math.random() * 26 + 'a');
            for (char ch : arr) {
                if (ch == c) {
                    continue Outer;
                }
            }
            arr[count++] = c;
        }
        return arr;
    }

}
