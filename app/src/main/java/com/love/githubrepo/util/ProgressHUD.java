package com.love.githubrepo.util;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;

import com.love.githubrepo.R;


/*
 * Custom progress bar HUD
 * */
public class   ProgressHUD extends Dialog {

    public ProgressHUD(Context context) {
        super(context);
    }

    private ProgressHUD(Context context, int theme) {
        super(context, theme);
    }

    public static ProgressDialog init(Context context, boolean cancelable, boolean isTransparent) {
        ProgressDialog dialog = new ProgressDialog(context);
        try {
            dialog.show();
        } catch (Exception e) {

        }
        //dialog.setContentView(R.layout.custom_progress_dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.custom_progress_bar);
        //ContentLoadingProgressBar loadingImageView = dialog.findViewById(R.id.loader_real_peg);
        return dialog;

    }

    public static ProgressDialog initTransparent(Context context, boolean cancelable, boolean isTransparent) {
        ProgressDialog dialog = new ProgressDialog(context);
        try {
            dialog.show();
        } catch (Exception e) {

        }
        //dialog.setContentView(R.layout.custom_progress_dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.custom_progress_transparent);
        //ContentLoadingProgressBar loadingImageView = dialog.findViewById(R.id.loader_real_peg);
        return dialog;

    }

}
