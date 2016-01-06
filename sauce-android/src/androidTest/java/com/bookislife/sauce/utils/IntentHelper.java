package com.bookislife.sauce.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by SidneyXu on 2016/01/04.
 */
public class IntentHelper {

    public static EmailBuilder withEmail(Context context) {
        return new EmailBuilder(context);
    }

    public static boolean isActivitySupported(Context context, Intent intent) {
        return intent.resolveActivity(context.getPackageManager()) != null;
    }

    public static abstract class Builder {
        protected Context context;

        protected Builder(Context context) {
            this.context = context;
        }

        abstract Intent build();

        public void startActivity() {
            Intent intent = build();
            context.startActivity(intent);
        }
    }

    public static class EmailBuilder extends Builder {

        private String type = "text/plain";
        private String subject;
        private String text;
        private String chooserTitle;
        private Uri attachment;
        private String email;

        protected EmailBuilder(Context context) {
            super(context);
        }

        @Override
        public Intent build() {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            if (type != null) {
                intent.setType(type);
            }
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, text);
            if (attachment != null) {
                intent.putExtra(Intent.EXTRA_STREAM, attachment);
            }
            if (chooserTitle == null) {
                return intent;
            }
            return Intent.createChooser(intent, chooserTitle);
        }

        public EmailBuilder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public EmailBuilder email(String email) {
            this.email = email;
            return this;
        }

        public EmailBuilder text(String text) {
            this.text = text;
            return this;
        }

        public EmailBuilder chooserTitle(String chooserTitle) {
            this.chooserTitle = chooserTitle;
            return this;
        }

        public EmailBuilder attachment(Uri uri) {
            this.attachment = uri;
            return this;
        }
    }

}
