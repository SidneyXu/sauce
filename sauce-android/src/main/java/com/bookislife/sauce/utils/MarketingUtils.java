package com.bookislife.sauce.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by SidneyXu on 2015/12/21.
 */
public class MarketingUtils {

    private static final String GOOGLE_PLAY_BASE_URL = "https://play.google.com/store/apps/details?id=";
    private static final String GOOGLE_PLAY_MARKET_URL = "market://details?id=";

    public static void showAppOnGooglePlay(Context context, String packageName) {
        Intent it = new Intent();
        it.setAction(Intent.ACTION_VIEW);
        Uri uri;
        try {
            uri = Uri.parse(GOOGLE_PLAY_MARKET_URL + packageName);
            it.setData(uri);
            context.startActivity(it);
        } catch (ActivityNotFoundException e) {
            uri = Uri.parse(GOOGLE_PLAY_BASE_URL + packageName);
            it.setData(uri);
            context.startActivity(it);
        }

    }
}
