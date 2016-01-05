package com.bookislife.sauce.utils;

import android.content.Context;
import android.graphics.*;

/**
 * Created by SidneyXu on 2016/01/05.
 */
public class ImageDecoder {

    public enum ScalingStrategy {
        CROP, FIT
    }

    public static Bitmap decocdeResource(Context context, int resourceId, int dstWidth, int dstHeight) {
        Bitmap unscaledBitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);
        return Bitmap.createScaledBitmap(unscaledBitmap, dstWidth, dstHeight, true);
    }

    public static Bitmap decodeScaledFile(String pathName, int dstWidth, int dstHeight, ScalingStrategy scalingStrategy) {
        Bitmap unscaledBitmap = decodeFile(pathName, dstWidth, dstHeight, scalingStrategy);
        return createScaledBitmap(unscaledBitmap, dstWidth, dstHeight, scalingStrategy);
    }

    public static Bitmap createScaledBitmap(Bitmap unscaledBitmap, int dstWidth, int dstHeight, ScalingStrategy scalingStrategy) {
        Rect srcRect = calculateSrcRect(unscaledBitmap.getWidth(), unscaledBitmap.getHeight(), dstWidth, dstHeight, scalingStrategy);
        Rect dstRect = calculateDstRect(unscaledBitmap.getWidth(), unscaledBitmap.getHeight(), dstWidth, dstHeight, scalingStrategy);
        Bitmap scaledBitmap = Bitmap.createBitmap(dstRect.width(), dstRect.height(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(scaledBitmap);
        canvas.drawBitmap(unscaledBitmap, srcRect, dstRect, new Paint(Paint.FILTER_BITMAP_FLAG));
        return scaledBitmap;
    }

    private static Rect calculateSrcRect(int srcWidth, int srcHeight, int dstWidth, int dstHeight, ScalingStrategy scalingStrategy) {
        if (scalingStrategy == ScalingStrategy.CROP) {
            float srcAspect = (float) srcWidth / srcHeight;
            float dstAspect = (float) dstWidth / dstHeight;
            if (srcAspect > dstAspect) {
                int srcRectWidth = (int) (srcHeight * dstAspect);
                int srcRectLeft = (srcWidth - srcRectWidth) / 2;
                return new Rect(srcRectLeft, 0, srcRectLeft + srcRectWidth, srcHeight);
            } else {
                int srcRectHeight = (int) (srcWidth / dstAspect);
                int scrRectTop = (srcHeight - srcRectHeight) / 2;
                return new Rect(0, scrRectTop, srcWidth, scrRectTop + srcRectHeight);
            }
        } else {
            return new Rect(0, 0, srcWidth, srcHeight);
        }
    }

    private static Rect calculateDstRect(int srcWidth, int srcHeight, int dstWidth, int dstHeight, ScalingStrategy scalingStrategy) {
        if (scalingStrategy == ScalingStrategy.FIT) {
            float srcAspect = (float) srcWidth / srcHeight;
            float dstAspect = (float) dstWidth / dstHeight;
            if (srcAspect > dstAspect) {
                return new Rect(0, 0, dstWidth, (int) (dstWidth / srcAspect));
            } else {
                return new Rect(0, 0, (int) (dstHeight * srcAspect), dstHeight);
            }
        } else {
            return new Rect(0, 0, dstWidth, dstHeight);
        }
    }

    public static Bitmap decodeFile(String pathName, int dstWidth, int dstHeight, ScalingStrategy scalingStrategy) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);
        options.inJustDecodeBounds = false;
        options.inSampleSize = calculateSampleSize(options.outWidth, options.outHeight, dstWidth, dstHeight, scalingStrategy);
        return BitmapFactory.decodeFile(pathName, options);
    }

    private static int calculateSampleSize(int srcWidth, int srcHeight, int dstWidth, int dstHeight, ScalingStrategy scalingStrategy) {
        if (scalingStrategy == ScalingStrategy.FIT) {
            float srcAspect = (float) srcWidth / srcHeight;
            float dstAspect = (float) dstWidth / dstHeight;
            if (srcAspect > dstAspect) {
                return srcWidth / dstWidth;
            } else {
                return srcHeight / dstHeight;
            }
        } else {
            float srcAspect = (float) srcWidth / srcHeight;
            float dstAspect = (float) dstWidth / dstHeight;
            if (srcAspect > dstAspect) {
                return srcHeight / dstHeight;
            } else {
                return srcWidth / dstWidth;
            }
        }
    }

}
