package com.bookislife.sauce.providers;

import android.content.Context;
import com.squareup.picasso.Picasso;

/**
 * Created by SidneyXu on 2016/01/14.
 */
public class PicassoImageProvider extends ImageProvider {

    public PicassoImageProvider(final Context context) {
        super(context);
    }

    @Override
    protected void execute(ImageRequest request) {
        Picasso.with(context)
                .load(request.uri)
                .placeholder(request.placeHolder)
                .into(request.imageView);
    }
}
