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

    public ImageRequest load(String url) {
        return new ImageRequest(this)
                .load(Uri.parse(url));
    }

    public static class ImageRequest {

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

        public static final int GLIDE = 1;
        public static final int PICASSO = 2;
        public static final int DEFAULT = 0;

        public static int type = DEFAULT;

        public ImageProvider getImageProvider(Context context) {
            switch (type) {
                case 0:
                    try {
                        Class.forName("com.bumptech.glide.Glide");
                        return new GlideImageProvider(context);
                    } catch (ClassNotFoundException ignored) {
                    }
                    try {
                        Class.forName("com.squareup.picasso.Picasso");
                        return new PicassoImageProvider(context);
                    } catch (ClassNotFoundException ignored) {
                    }
                    break;
                case GLIDE:
                    return new GlideImageProvider(context);
                case PICASSO:
                    return new PicassoImageProvider(context);
            }
            throw new IllegalStateException("Glide or Picasso is needed.");
        }

        public static void select(int type) {
            ImageProviderSelector.type = type;
        }

    }
}
