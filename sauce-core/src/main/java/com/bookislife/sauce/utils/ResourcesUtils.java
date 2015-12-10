package com.bookislife.sauce.utils;

import java.util.Enumeration;
import java.util.ResourceBundle;

/**
 * Created by SidneyXu on 2015/12/10.
 */
public class ResourcesUtils {

    public static boolean hasKey(ResourceBundle rb, String key) {
        if (rb != null) {
            Enumeration<String> keySet = rb.getKeys();
            return key != null && hasKey(keySet, key);
        }
        return false;

    }

    private static boolean hasKey(Enumeration<String> keySet, String key) {
        boolean exist = false;
        if (keySet != null) {
            while (keySet.hasMoreElements()) {
                if (key.equals(keySet.nextElement())) {
                    exist = true;
                    break;
                }
            }
        }
        return exist;
    }

    public static String getProperty(ResourceBundle rb, String key) {
        String val = null;
        if (hasKey(rb, key)) {
            val = rb.getString(key);
        }
        return val;
    }
}
