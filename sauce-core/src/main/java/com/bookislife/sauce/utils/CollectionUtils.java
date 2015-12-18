package com.bookislife.sauce.utils;

import java.lang.reflect.Array;
import java.util.Collection;

/**
 * Created by SidneyXu on 2015/12/18.
 */
public class CollectionUtils {

    public static boolean isEmpty(Collection<?> col) {
        return col == null || col.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> col) {
        return !isEmpty(col);
    }

    public static <T> T[] connectArray(Class<T> clazz, Object[] arr1, Object[] arr2) {
        T[] arr = (T[]) Array.newInstance(clazz, arr1.length + arr2.length);
        System.arraycopy(arr1, 0, arr, 0, arr1.length);
        System.arraycopy(arr2, 0, arr, arr1.length, arr2.length);
        return arr;
    }

    public static String join(Collection<?> collection, String sign) {
        sign = sign == null ? "," : sign;
        StringBuilder sb = new StringBuilder();

        for (Object obj : collection) {
            sb.append(obj.toString());
            sb.append(sign);
        }
        if (sb.lastIndexOf(sign) == sb.length() - 1) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
