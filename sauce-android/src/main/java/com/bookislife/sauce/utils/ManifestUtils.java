package com.bookislife.sauce.utils;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.*;
import android.os.Bundle;

import java.util.List;

/**
 * Created by SidneyXu on 2015/12/14.
 */
public class ManifestUtils {

    public static String getAppVersion(Context context) {
        PackageInfo packageInfo = getPackageInfo(context);
        if (packageInfo == null) return null;
        return packageInfo.versionName;
    }

    public static String getAppName(Context context) {
        ApplicationInfo applicationInfo = getApplicationInfo(context);
        if (applicationInfo == null) return null;
        PackageManager manager = context.getPackageManager();
        CharSequence label = manager.getApplicationLabel(
                applicationInfo
        );
        if (label == null) return null;
        return label.toString();
    }

    public static Bundle getMetaData(Context context) {
        ApplicationInfo applicationInfo = getApplicationInfo(context);
        if (applicationInfo != null) {
            return applicationInfo.metaData;
        }
        return null;
    }

    public static int getApplicationIcon(Context context) {
        return context.getApplicationInfo().icon;
    }

    public static boolean hasPermission(Context context,
                                        String permission) {
        PackageManager packageManager = context.getPackageManager();
        try {
            return packageManager.checkPermission(permission,
                    context.getPackageName()) == PackageManager.PERMISSION_GRANTED;
        } catch (NullPointerException e) {
            return false;
        }
    }

    public static boolean hasPermissions(Context context,
                                         String[] permissions) {
        for (String permission : permissions) {
            if (!hasPermission(context, permission)) {
                return false;
            }
        }
        return true;
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageManager manager = context.getPackageManager();
        try {
            return manager.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS
                            | PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    private static ApplicationInfo getApplicationInfo(Context context) {
        PackageManager manager = context.getPackageManager();
        try {
            return manager.getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    public static ServiceInfo getServiceInfo(Context context,
                                             Class<? extends Service> clazz) {
        PackageManager manager = context.getPackageManager();
        try {
            return manager.getServiceInfo(new ComponentName(context, clazz),
                    PackageManager.GET_CONFIGURATIONS
                            | PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    public static boolean isServiceDeclared(Context context,
                                            Class<? extends Service> service) {
        return getServiceInfo(context, service) != null;
    }

    public static ActivityInfo getReceiverInfo(Context context,
                                               Class<? extends BroadcastReceiver> clazz) {
        PackageManager manager = context.getPackageManager();
        try {
            return manager.getReceiverInfo(new ComponentName(context, clazz),
                    PackageManager.GET_CONFIGURATIONS
                            | PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    public static boolean isReceiverDeclared(Context context,
                                             Class<? extends BroadcastReceiver> receiver,
                                             String permission,
                                             Intent[] actions) {
        ActivityInfo info = getReceiverInfo(context, receiver);
        if (null == info) return false;
        if (null != permission && !permission.equals(info.permission)) return false;
        PackageManager packageManager = context.getPackageManager();
        for (Intent action : actions) {
            List<ResolveInfo> resolveInfos = packageManager.queryBroadcastReceivers(action, PackageManager.GET_CONFIGURATIONS
                    | PackageManager.GET_META_DATA);
            if (null == resolveInfos || resolveInfos.isEmpty()) return false;
            for (ResolveInfo resolveInfo : resolveInfos) {
                if (null != resolveInfo.activityInfo && receiver.getCanonicalName().equals(resolveInfo.activityInfo.name))
                    return true;
            }
        }
        return false;
    }

    public static boolean isIntentSupported(Context context, Intent intent) {
        PackageManager packageManager = context.getPackageManager();
        return intent.resolveActivity(packageManager) != null;
    }
}
