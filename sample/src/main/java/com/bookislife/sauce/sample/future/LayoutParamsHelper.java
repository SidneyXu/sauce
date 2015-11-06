package com.bookislife.sauce.sample.future;

import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by mrseasons on 2015/10/26.
 */
public class LayoutParamsHelper {


    public static ViewGroup.LayoutParams newViewGroupParams(boolean matchWidth,
                                                            boolean matchHeight) {
        return new ViewGroup.LayoutParams(
                matchWidth ? ViewGroup.LayoutParams.MATCH_PARENT : ViewGroup.LayoutParams.WRAP_CONTENT,
                matchHeight ? ViewGroup.LayoutParams.MATCH_PARENT : ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public static LinearLayout.LayoutParams newLinearLayoutParams(boolean matchWidth,
                                                                  boolean matchHeight) {
        return new LinearLayout.LayoutParams(
                matchWidth ? LinearLayout.LayoutParams.MATCH_PARENT : LinearLayout.LayoutParams.WRAP_CONTENT,
                matchHeight ? LinearLayout.LayoutParams.MATCH_PARENT : LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public static class Builder {
        private int width = ViewGroup.LayoutParams.WRAP_CONTENT;
        private int height = ViewGroup.LayoutParams.WRAP_CONTENT;

        public Builder() {
        }

        public Builder height(int height) {
            this.height = height;
            return this;
        }

        public Builder width(int width) {
            this.width = width;
            return this;
        }

        public Builder matchHeight() {
            this.height = ViewGroup.LayoutParams.MATCH_PARENT;
            return this;
        }

        public Builder matchWidth() {
            this.width = ViewGroup.LayoutParams.MATCH_PARENT;
            return this;
        }

        public Builder wrapHeight() {
            this.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            return this;
        }

        public Builder wrapWidth() {
            this.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            return this;
        }

        public ViewGroup.LayoutParams buildViewGroupParams() {
            return new ViewGroup.LayoutParams(width, height);
        }

        public LinearLayout.LayoutParams buildLinearLayoutParams() {
            return new LinearLayout.LayoutParams(width, height);
        }
    }
}
