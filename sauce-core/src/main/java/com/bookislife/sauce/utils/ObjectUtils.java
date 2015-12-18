package com.bookislife.sauce.utils;

/**
 * Created by SidneyXu on 2015/12/18.
 */
public class ObjectUtils {

    public static <T> boolean equals(T arg1, T arg2) {
        if (arg1 == null && arg2 == null) {
            return true;
        }
        if (arg1 == null || arg2 == null) {
            return false;
        }
        return arg1.equals(arg2);
    }
}
