package com.bookislife.sauce.providers;

import android.content.Context;
import com.bumptech.glide.Glide;

/**
 * Created by SidneyXu on 2015/09/18.
 */
public class GlideImageProvider extends ImageProvider {

    public GlideImageProvider(final Context context) {
        super(context);
    }

    @Override
    protected void execute(ImageRequest request) {
        if (request.placeHolder != null) {
            Glide.with(context)
                    .load(request.uri)
                    .placeholder(request.placeHolder)
                    .into(request.imageView);
        } else {
            Glide.with(context)
                    .load(request.uri)
                    .into(request.imageView);
        }
    }

}
