package com.bookislife.sauce.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.InvocationTargetException;
import java.util.Locale;

/**
 * Created by SidneyXu on 2015/12/14.
 */
public class DeviceUtils {

    public static boolean isAppExist(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            packageManager.getPackageInfo(context.getPackageName(), 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static String getOSVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    public static String getModel() {
        return android.os.Build.MODEL;
    }

    public static String getLanguage() {
        return Locale.getDefault().getLanguage();
    }

    public static String getCountry() {
        return Locale.getDefault().getCountry();
    }

    public static String getResolution(final Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.widthPixels + "*"
                + metrics.heightPixels;
    }

    public static void hideSoftInput(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) return;
        IBinder binder = view.getWindowToken();
        if (binder == null) return;
        inputMethodManager.hideSoftInputFromWindow(binder, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static boolean isNetworkAvailable(final Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo anInfo : info) {
                    if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean checkNetworkConnection(Context context, int type) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo anInfo : info) {
                    if (anInfo.getType() == type && anInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static String getMacAddress(Context context) {
        if (!ManifestUtils.hasPermission(context, Manifest.permission.ACCESS_WIFI_STATE)) {
            return null;
        }
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        String macAddress = "";
        if (info.getMacAddress() != null) {
            macAddress = info.getMacAddress().replaceAll(":", "");
        }
        return macAddress;
    }

    /**
     * Check whether the app is installed from Google Play for security.
     *
     * @param context
     * @return
     */
    public static boolean isInstallFromGooglePlay(Context context) {
        String installerPackageName = context.getPackageManager()
                .getInstallerPackageName(context.getPackageName());
        return installerPackageName != null
                && installerPackageName.startsWith("com.google.android");
    }

    /**
     * Check whether the app is debuggable for security.
     *
     * @param context
     * @return
     */
    public static boolean isDebuggable(Context context) {
        return (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
    }

    /**
     * Check whether the app is running on an emulator for security.
     *
     * @param context
     * @return
     */
    public static boolean isEmulator(Context context) {
        try {
            Class systemPropertyClazz = Class.forName("android.os.SystemProperties");
            boolean kernelQemu = getProperty(systemPropertyClazz, "ro.kernel.qemu").length() > 0;
            boolean hardwareGoldfish = getProperty(systemPropertyClazz, "ro.hardware").equals("goldfish");
            boolean modelSdk = getProperty(systemPropertyClazz, "ro.product.model").equals("sdk");
            return kernelQemu || hardwareGoldfish || modelSdk;
        } catch (Exception e) {
            return false;
        }
    }

    private static String getProperty(Class systemPropertyClazz, String propertyName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return (String) systemPropertyClazz.getMethod("get", new Class[]{String.class})
                .invoke(systemPropertyClazz, new Object[]{propertyName});
    }

    public static int dip2px(Context context, float dip) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (scale * dip + 0.5f);
    }

    public static int px2dip(Context context, float px) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    public static int getHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int getWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }
}
