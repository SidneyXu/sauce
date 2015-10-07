package com.bookislife.sauce.providers;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;
import com.bookislife.sauce.files.AndroidFileHandle;

/**
 * Created by SidneyXu on 2015/09/18.
 */
public abstract class ImageProvider extends Providers {

    protected Context context;
    protected AndroidFileHandle fileHandle;

    public ImageProvider(Context context) {
        this.context = context;
    }

    protected abstract void execute(ImageRequest request);

    public ImageRequest load(AndroidFileHandle fileHandle) {
        this.fileHandle = fileHandle;
        return new ImageRequest(this)
                .load(fileHandle.toUri());
    }

    static class ImageRequest {

        Uri uri;
        Drawable placeHolder;
        ImageView imageView;

        private ImageProvider imageProvider;

        public ImageRequest(ImageProvider provider) {
            this.imageProvider = provider;
        }

        public ImageRequest load(Uri uri) {
            this.uri = uri;
            return this;
        }

        public ImageRequest placeHolder(Drawable drawable) {
            this.placeHolder = drawable;
            return this;
        }

        public void into(ImageView imageView) {
            this.imageView = imageView;
            imageProvider.execute(this);
        }

    }

    public static class ImageProviderSelector {

        public ImageProvider getImageProvider(Context context) {
            return new GlideImageProvider(context);
        }
    }
}
