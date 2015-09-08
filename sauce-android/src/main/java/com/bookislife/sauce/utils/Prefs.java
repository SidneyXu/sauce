package com.bookislife.sauce.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

/**
 * Created by mrseasons on 2015/09/08.
 */
public class Prefs {

    public static void putMap(Context context, String databaseTag,
                              Map<String, String> dataMap) {
        SharedPreferences share = context.getSharedPreferences(databaseTag,
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = share.edit();
        for (Map.Entry<String, String> entry : dataMap.entrySet()) {
            editor.putString(entry.getKey(), entry.getValue());
        }
        editor.commit();
    }

    public static void putString(Context context, String databaseTag,
                                 String dataTag, String data) {
        SharedPreferences share = context.getSharedPreferences(databaseTag,
                Activity.MODE_PRIVATE);
        share.edit().putString(dataTag, data).commit();
    }

    public static void putInt(Context context, String databaseTag,
                              String dataTag, int data) {
        SharedPreferences share = context.getSharedPreferences(databaseTag,
                Activity.MODE_PRIVATE);
        share.edit().putInt(dataTag, data).commit();
    }

    public static void putBoolean(Context context, String databaseTag,
                                  String dataTag, boolean data) {
        SharedPreferences share = context.getSharedPreferences(databaseTag,
                Activity.MODE_PRIVATE);
        share.edit().putBoolean(dataTag, data).commit();
    }

    public static void putLong(Context context, String databaseTag,
                               String dataTag, long data) {
        SharedPreferences share = context.getSharedPreferences(databaseTag,
                Activity.MODE_PRIVATE);
        share.edit().putLong(dataTag, data).commit();
    }

    public static void putFloat(Context context, String databaseTag,
                                String dataTag, float data) {
        SharedPreferences share = context.getSharedPreferences(databaseTag,
                Activity.MODE_PRIVATE);
        share.edit().putFloat(dataTag, data).commit();
    }

    public static int getInt(Context context, String databaseTag,
                             String dataTag, int defaultValue) {
        SharedPreferences share = context.getSharedPreferences(databaseTag, 0);
        return share.getInt(dataTag, defaultValue);
    }

    public static boolean getBoolean(Context context, String databaseTag,
                                     String dataTag, boolean defaultValue) {
        SharedPreferences share = context.getSharedPreferences(databaseTag, 0);
        return share.getBoolean(dataTag, defaultValue);
    }

    public static long getLong(Context context, String databaseTag,
                               String dataTag, long defaultValue) {
        SharedPreferences share = context.getSharedPreferences(databaseTag, 0);
        return share.getLong(dataTag, defaultValue);
    }

    public static float getFloat(Context context, String databaseTag,
                                 String dataTag, float defaultValue) {
        SharedPreferences share = context.getSharedPreferences(databaseTag, 0);
        return share.getFloat(dataTag, defaultValue);
    }

    public static String getString(Context context, String databaseTag,
                                   String dataTag, String defaultValue) {
        SharedPreferences share = context.getSharedPreferences(databaseTag, 0);
        return share.getString(dataTag, defaultValue);
    }

    public static void remove(Context context, String databaseTag,
                              String dataTag) {
        SharedPreferences share = context.getSharedPreferences(databaseTag, 0);
        share.edit().remove(dataTag).commit();
    }

    public static void clear(Context context, String databaseTag) {
        SharedPreferences share = context.getSharedPreferences(databaseTag,
                Activity.MODE_PRIVATE);
        share.edit().clear().commit();
    }
}
