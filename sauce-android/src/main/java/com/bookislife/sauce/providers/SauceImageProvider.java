package com.bookislife.sauce.providers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import com.bookislife.sauce.SauceAndroid;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by mrseasons on 2015/09/18.
 */
public class SauceImageProvider extends ImageProvider {

    private ExecutorService service = Executors.newFixedThreadPool(1);

    public SauceImageProvider(final Context context) {
        super(context);
    }

    @Override
    protected void execute(final ImageRequest request) {
        if (request.placeHolder != null) {
            request.imageView.setImageDrawable(request.placeHolder);
        }
        service.execute(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = decodeByArray(context, fileHandle.tryReadBytes());

                SauceAndroid.getInstance().post(new Runnable() {
                    @Override
                    public void run() {
                        request.imageView.setImageBitmap(bitmap);
                    }
                });

            }
        });


    }

    public static Bitmap decodeByArray(byte[] data, int allowedWidth,
                                       int allowedHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(data, 0, data.length, options);

        options.inSampleSize = calcScale(options, allowedWidth, allowedHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(data, 0, data.length, options);
    }

    public static Bitmap decodeByArray(Context context, byte[] data) {
        final DisplayMetrics displayMetrics = context.getResources()
                .getDisplayMetrics();
        final int screenWidth = displayMetrics.widthPixels;
        final int screenHeight = displayMetrics.heightPixels;
        return decodeByArray(data, screenWidth, screenHeight);
    }

    private static int calcScale(BitmapFactory.Options options,
                                 int allowedWidth, int allowedHeight) {
        int scale = 1;
        if (options.outHeight > allowedHeight
                || options.outWidth > allowedWidth) {
            int scaleX = options.outWidth / allowedWidth;
            int scaleY = options.outHeight / allowedHeight;
            scale = scaleX > scaleY ? scaleX : scaleY;
            scale = scale <= 0 ? 1 : scale;
        }
        return scale;
    }

}
