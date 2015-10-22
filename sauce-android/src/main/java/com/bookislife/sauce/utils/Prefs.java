package com.bookislife.sauce.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

/**
 * This class make it easier to access SharedPreferences files on the Android platform.
 */
public class Prefs {

    /**
     * Save string values from the specified map.
     *
     * @param context   the Android context
     * @param prefsName the SharedPreferences name
     * @param dataMap   the map data
     */
    public static void putMap(Context context, String prefsName,
                              Map<String, String> dataMap) {
        SharedPreferences share = context.getSharedPreferences(prefsName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = share.edit();
        for (Map.Entry<String, String> entry : dataMap.entrySet()) {
            editor.putString(entry.getKey(), entry.getValue());
        }
        editor.commit();
    }

    /**
     * Save string values from the specified map.
     *
     * @param share   the SharedPreferences instance
     * @param dataMap the map data
     */
    public static void putMap(SharedPreferences share,
                              Map<String, String> dataMap) {
        SharedPreferences.Editor editor = share.edit();
        for (Map.Entry<String, String> entry : dataMap.entrySet()) {
            editor.putString(entry.getKey(), entry.getValue());
        }
        editor.commit();
    }

    /**
     * Save a string value.
     *
     * @param context   the Android context
     * @param prefsName the SharedPreferences name
     * @param dataTag   the data tag
     * @param data      the data value
     */
    public static void putString(Context context, String prefsName,
                                 String dataTag, String data) {
        SharedPreferences share = context.getSharedPreferences(prefsName,
                Context.MODE_PRIVATE);
        share.edit().putString(dataTag, data).commit();
    }

    /**
     * Save a string value.
     *
     * @param share   the SharedPreferences instance
     * @param dataTag the data tag
     * @param data    the data value
     */
    public static void putString(SharedPreferences share,
                                 String dataTag, String data) {
        share.edit().putString(dataTag, data).commit();
    }

    /**
     * Save an int value.
     *
     * @param context   the Android context
     * @param prefsName the SharedPreferences name
     * @param dataTag   the data tag
     * @param data      the data value
     */
    public static void putInt(Context context, String prefsName,
                              String dataTag, int data) {
        SharedPreferences share = context.getSharedPreferences(prefsName,
                Context.MODE_PRIVATE);
        share.edit().putInt(dataTag, data).commit();
    }

    /**
     * Save an int value.
     *
     * @param share   the SharedPreferences instance
     * @param dataTag the data tag
     * @param data    the data value
     */
    public static void putInt(SharedPreferences share,
                              String dataTag, int data) {
        share.edit().putInt(dataTag, data).commit();
    }

    /**
     * Save a boolean value.
     *
     * @param context   the Android context
     * @param prefsName the SharedPreferences name
     * @param dataTag   the data tag
     * @param data      the data value
     */
    public static void putBoolean(Context context, String prefsName,
                                  String dataTag, boolean data) {
        SharedPreferences share = context.getSharedPreferences(prefsName,
                Context.MODE_PRIVATE);
        share.edit().putBoolean(dataTag, data).commit();
    }

    /**
     * Save a boolean value.
     *
     * @param share   the SharedPreferences instance
     * @param dataTag the data tag
     * @param data    the data value
     */
    public static void putBoolean(SharedPreferences share,
                                  String dataTag, boolean data) {
        share.edit().putBoolean(dataTag, data).commit();
    }

    /**
     * Save a long value.
     *
     * @param context   the Android context
     * @param prefsName the SharedPreferences name
     * @param dataTag   the data tag
     * @param data      the data value
     */
    public static void putLong(Context context, String prefsName,
                               String dataTag, long data) {
        SharedPreferences share = context.getSharedPreferences(prefsName,
                Context.MODE_PRIVATE);
        share.edit().putLong(dataTag, data).commit();
    }

    /**
     * Save a long value.
     *
     * @param share   the SharedPreferences instance
     * @param dataTag the data tag
     * @param data    the data value
     */
    public static void putLong(SharedPreferences share,
                               String dataTag, long data) {
        share.edit().putLong(dataTag, data).commit();
    }

    /**
     * Save a float value.
     *
     * @param context   the Android context
     * @param prefsName the SharedPreferences name
     * @param dataTag   the data tag
     * @param data      the data value
     */
    public static void putFloat(Context context, String prefsName,
                                String dataTag, float data) {
        SharedPreferences share = context.getSharedPreferences(prefsName,
                Context.MODE_PRIVATE);
        share.edit().putFloat(dataTag, data).commit();
    }

    /**
     * Save a float value.
     *
     * @param share   the SharedPreferences instance
     * @param dataTag the data tag
     * @param data    the data value
     */
    public static void putFloat(SharedPreferences share,
                                String dataTag, float data) {
        share.edit().putFloat(dataTag, data).commit();
    }

    /**
     * Return the int value if possible.
     *
     * @param context      the Android context
     * @param prefsName    the SharedPreferences name
     * @param dataTag      the data tag
     * @param defaultValue the default value
     * @return the int value or defaultValue if not exist
     */
    public static int getInt(Context context, String prefsName,
                             String dataTag, int defaultValue) {
        SharedPreferences share = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE);
        return share.getInt(dataTag, defaultValue);
    }

    /**
     * Return the int value if possible.
     *
     * @param share        the SharedPreferences instance
     * @param dataTag      the data tag
     * @param defaultValue the default value
     * @return the int value or defaultValue if not exist
     */
    public static int getInt(SharedPreferences share,
                             String dataTag, int defaultValue) {
        return share.getInt(dataTag, defaultValue);
    }

    /**
     * Return the boolean value if possible.
     *
     * @param context      the Android context
     * @param prefsName    the SharedPreferences name
     * @param dataTag      the data tag
     * @param defaultValue the default value
     * @return the boolean value or defaultValue if not exist
     */
    public static boolean getBoolean(Context context, String prefsName,
                                     String dataTag, boolean defaultValue) {
        SharedPreferences share = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE);
        return share.getBoolean(dataTag, defaultValue);
    }

    /**
     * Return the boolean value if possible.
     *
     * @param share        the SharedPreferences instance
     * @param dataTag      the data tag
     * @param defaultValue the default value
     * @return the boolean value or defaultValue if not exist
     */
    public static boolean getBoolean(SharedPreferences share,
                                     String dataTag, boolean defaultValue) {
        return share.getBoolean(dataTag, defaultValue);
    }

    /**
     * Return the long value if possible.
     *
     * @param context      the Android context
     * @param prefsName    the SharedPreferences name
     * @param dataTag      the data tag
     * @param defaultValue the default value
     * @return the long value or defaultValue if not exist
     */
    public static long getLong(Context context, String prefsName,
                               String dataTag, long defaultValue) {
        SharedPreferences share = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE);
        return share.getLong(dataTag, defaultValue);
    }

    /**
     * Return the long value if possible.
     *
     * @param share        the SharedPreferences instance
     * @param dataTag      the data tag
     * @param defaultValue the default value
     * @return the long value or defaultValue if not exist
     */
    public static long getLong(SharedPreferences share,
                               String dataTag, long defaultValue) {
        return share.getLong(dataTag, defaultValue);
    }

    /**
     * Return the float value if possible.
     *
     * @param context      the Android context
     * @param prefsName    the SharedPreferences name
     * @param dataTag      the data tag
     * @param defaultValue the default value
     * @return the float value or defaultValue if not exist
     */
    public static float getFloat(Context context, String prefsName,
                                 String dataTag, float defaultValue) {
        SharedPreferences share = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE);
        return share.getFloat(dataTag, defaultValue);
    }

    /**
     * Return the float value if possible.
     *
     * @param share        the SharedPreferences instance
     * @param dataTag      the data tag
     * @param defaultValue the default value
     * @return the float value or defaultValue if not exist
     */
    public static float getFloat(SharedPreferences share,
                                 String dataTag, float defaultValue) {
        return share.getFloat(dataTag, defaultValue);
    }

    /**
     * Return the String value if possible.
     *
     * @param context      the Android context
     * @param prefsName    the SharedPreferences name
     * @param dataTag      the data tag
     * @param defaultValue the default value
     * @return the String value or defaultValue if not exist
     */
    public static String getString(Context context, String prefsName,
                                   String dataTag, String defaultValue) {
        SharedPreferences share = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE);
        return share.getString(dataTag, defaultValue);
    }

    /**
     * Return the String value if possible.
     *
     * @param share        the SharedPreferences instance
     * @param dataTag      the data tag
     * @param defaultValue the default value
     * @return the String value or defaultValue if not exist
     */
    public static String getString(SharedPreferences share,
                                   String dataTag, String defaultValue) {
        return share.getString(dataTag, defaultValue);
    }

    /**
     * Remove a value by the specified tag from the SharedPreferences file.
     *
     * @param context   the Android context
     * @param prefsName the SharedPreferences name
     * @param dataTag   the data tag for removed
     */
    public static void remove(Context context, String prefsName,
                              String dataTag) {
        SharedPreferences share = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE);
        share.edit().remove(dataTag).commit();
    }

    /**
     * Remove a value by the specified tag from the SharedPreferences file.
     *
     * @param share   the SharedPreferences instance
     * @param dataTag the data tag for removed
     */
    public static void remove(SharedPreferences share,
                              String dataTag) {
        share.edit().remove(dataTag).commit();
    }

    /**
     * Clear all in the specified SharedPreferences file.
     *
     * @param context   the Android context
     * @param prefsName the SharedPreferences name
     */
    public static void clear(Context context, String prefsName) {
        SharedPreferences share = context.getSharedPreferences(prefsName,
                Context.MODE_PRIVATE);
        share.edit().clear().commit();
    }

    /**
     * Clear all in the specified SharedPreferences file.
     *
     * @param share the SharedPreferences instance
     */
    public static void clear(SharedPreferences share) {
        share.edit().clear().clear();
    }
}
