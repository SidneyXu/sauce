package com.bookislife.sauce.sample;

import android.app.Application;
import com.bookislife.sauce.Sauce;
import com.bookislife.sauce.SauceAndroid;

/**
 * Created by SidneyXu on 2015/09/25.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Sauce.initialize(new SauceAndroid(this));
    }
}
