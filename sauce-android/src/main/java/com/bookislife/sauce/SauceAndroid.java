package com.bookislife.sauce;

import android.content.Context;
import com.bookislife.sauce.files.AndroidFileHandles;
import com.bookislife.sauce.files.FileHandles;

/**
 * Created by mrseasons on 2015/09/08.
 */
public class SauceAndroid extends SaucePlatform {

    public final Context context;

    private static SauceAndroid current;

    public static SauceAndroid getInstance() {
        if (null == current) {
            throw new RuntimeException("Call Sauce.initialize() before using Sauce.");
        }
        return current;
    }

    public SauceAndroid(Context context) {
        this.context = context.getApplicationContext();
        current = this;
    }

    @Override
    protected FileHandles getFiles() {
        return new AndroidFileHandles();
    }
}
