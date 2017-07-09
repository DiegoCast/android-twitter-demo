package com.diegocast.twitterapp.presentation;

import android.content.Context;
import android.support.v7.app.AlertDialog;

public class Utils {
    public static AlertDialog showError(Context context, String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                (dialog, which) -> dialog.dismiss());
        return alertDialog;
    }
}
