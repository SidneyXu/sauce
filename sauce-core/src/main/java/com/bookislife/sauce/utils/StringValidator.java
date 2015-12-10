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
     * Whether the param only contains alphabet, number or other half-width letters or not.
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

    /**
     * Whether the param only Japanese Katakana or not.
     *
     * @param str
     * @return
     */
    public static boolean isKatakana(String str) {
        return !isEmpty(str) && str.matches("^[ァ-ヶー　＆’，、－．・]+$");
    }

    /**
     * Whether the param only contains half-width letters or not.
     *
     * @param str
     * @return
     */
    public static boolean isHalfWidth(String str) {
        return !isEmpty(str) && str.matches("^[-~｡-ﾟ]+$");
    }

    /**
     * Whether the param only contains full-width letters or not.
     *
     * @param str
     * @return
     */
    public static boolean isFullWidth(String str) {
        return !isEmpty(str) && str.matches("^[^-~｡-ﾟ]+$");
    }

    /**
     * Whether the param contains prohibited letters of file name or not.
     *
     * @param str
     * @return
     */
    public static boolean containsProhibitedFileName(String str) {
        boolean result = false;
        if (str != null) {
            for (int i = 0; i < str.length(); i++) {
                if (result) {
                    break;
                } else {
                    result = isFilenameProhibitedChar(str.charAt(i));
                }
            }
        }

        return result;
    }

    private static boolean isFilenameProhibitedChar(char param) {
        boolean result = false;

        String ngStr = "\\/:*?\"<>|";
        if (ngStr.indexOf(param) != -1) {
            result = true;
        }

        return result;
    }

    /**
     * Whether the length of passed string is shorter than or equal to the specified max length or not.
     *
     * @param str
     * @param maxLength
     * @return
     */
    public static boolean isShorterThanOrEqualToLength(String str, int maxLength) {
        boolean result = false;

        if (str != null) {
            if (str.length() <= maxLength) {
                result = true;
            }
        }

        return result;
    }

    /**
     * Whether the length of passed string is longer than or equal to the specified min length or not.
     *
     * @param str
     * @param minLength
     * @return
     */
    public static boolean isLongerThanOrEqualToLength(String str, int minLength) {
        boolean result = false;

        if (str != null) {
            if (str.length() >= minLength) {
                result = true;
            }
        }

        return result;
    }

    /**
     * Whether the length of passed string is in the range of the specified length.
     *
     * @param str
     * @param minLength
     * @param maxLength
     * @return
     */
    public static boolean isLengthInRangeOf(String str, int minLength, int maxLength) {
        return isLongerThanOrEqualToLength(str, minLength) && isShorterThanOrEqualToLength(str, maxLength);
    }

    /**
     * Whether the passed string is empty or null.
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return null == str || "".equals(str);
    }
}
