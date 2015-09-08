package com.bookislife.sauce.utils;

import android.os.Environment;

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
        File dir = getSDCardDir();
        return dir != null;
    }
}
