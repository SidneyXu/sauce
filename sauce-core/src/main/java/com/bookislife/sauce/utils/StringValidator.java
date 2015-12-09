package com.bookislife.sauce.utils;

/**
 * Created by SidneyXu on 2015/12/09.
 */
public class StringValidator {

    /**
     * Whether the param only contains alphabet or not.
     *
     * @param str
     * @return
     */
    public static boolean isAlphabet(String str) {
        return !isEmpty(str) && str.matches("^[a-zA-Z]+$");
    }

    /**
     * Whether the param only contains number or not.
     *
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        return !isEmpty(str) && str.matches("^[0-9]+$");
    }

    /**
     * Whether the param only contains alphabet, number or other half-width words or not.
     *
     * @param str
     * @return
     */
    public static boolean isAscii(String str) {
        return !isEmpty(str) && str.matches("^[a-zA-Z0-9 -/:-@\\[-\\`\\{-\\~]+$");
    }

    /**
     * Whether the param is a mail address or not.
     *
     * @param str
     * @return
     */
    public static boolean isMailAddress(String str) {
        return !isEmpty(str) && str.matches("[0-9a-zA-Z._\\-]+@[0-9a-zA-Z_\\-]+(\\.[0-9a-zA-Z_\\-]+){1,}");
    }

    /**
     * Whether the param only Japanese Hiragana or not.
     *
     * @param str
     * @return
     */
    public static boolean isHiragana(String str) {
        return !isEmpty(str) && str.matches("^[ぁ-んー　]+$");
    }


    private static boolean isEmpty(String str) {
        return null == str || "".equals(str);
    }
}
