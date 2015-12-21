package com.bookislife.sauce.utils;

import android.app.AlertDialog;
import android.content.Context;

/**
 * Created by SidneyXu on 2015/12/21.
 */
public class DialogUtils {

    public static void showDefaultDialog(Context context, String title, String message) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .create()
                .show();
    }
}
