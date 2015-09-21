package com.bookislife.sauce;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.bookislife.sauce.files.AndroidFileHandles;
import com.bookislife.sauce.files.FileHandles;

/**
 * The class represents Android platform of the sauce Library.
 *
 * @author SidneyXu
 */
public class SauceAndroid extends SaucePlatform {

    public final Context context;

    private static SauceAndroid current;

    private Handler mainHandler;

    public static SauceAndroid getInstance() {
        if (null == current) {
            throw new RuntimeException("Call Sauce.initialize() before using Sauce.");
        }
        return current;
    }

    /**
     * Construct the Android platform.
     *
     * @param context the Android Context
     */
    public SauceAndroid(Context context) {
        this.context = context.getApplicationContext();
        current = this;
        mainHandler = new Handler(Looper.getMainLooper());
    }

    /**
     * Get the FileHandles of Android platform.
     *
     * @return the FileHandles instance of Android platform
     */
    @Override
    protected FileHandles getFiles() {
        return new AndroidFileHandles();
    }

    public void post(Runnable runnable) {
        mainHandler.post(runnable);
    }
}
