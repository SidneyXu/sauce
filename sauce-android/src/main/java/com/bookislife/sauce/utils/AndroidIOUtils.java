package com.bookislife.sauce.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import com.bookislife.sauce.SauceAndroid;

import java.io.File;

/**
 * Created by mrseasons on 2015/09/08.
 */
public class AndroidIOUtils extends IOUtils {

    public static File getSDCardDir() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory();
        }
        return null;
    }

    public static boolean isSDCardEnabled() {
        return hasPermission(SauceAndroid.getInstance().context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) && null != getSDCardDir();
    }

    /**
     * Check whether the permission is added or not.
     *
     * @param context    the android context
     * @param permission the required permission
     * @return True if has permission. False otherwise.
     */
    public static boolean hasPermission(final Context context,
                                        final String permission) {
        PackageManager packageManager = context.getPackageManager();
        try {
            return packageManager.checkPermission(permission,
                    context.getPackageName()) == PackageManager.PERMISSION_GRANTED;
        } catch (NullPointerException e) {
            return false;
        }
    }
}
