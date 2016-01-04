package com.bookislife.sauce.utils;

import android.content.Context;
import android.graphics.Bitmap;
import com.android.volley.toolbox.ImageLoader;

/**
 * Created by SidneyXu on 2016/01/04.
 */
public class VolleyBitmapCache extends LruBitmapCache implements ImageLoader.ImageCache {

    public VolleyBitmapCache(int maxSize) {
        super(maxSize);
    }

    public VolleyBitmapCache(Context context) {
        super(context);
    }

    @Override
    public Bitmap getBitmap(String url) {
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }
}
