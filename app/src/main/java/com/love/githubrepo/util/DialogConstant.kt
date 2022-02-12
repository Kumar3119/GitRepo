package com.love.githubrepo.util


import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.love.githubrepo.R
import java.util.*


object DialogConstant {

    fun noInternetDialog(context: Context, tryAgainClick: () -> Unit?) {
        val dialogInternet = Dialog(context, R.style.MyApplicationDialog)
        dialogInternet.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogInternet.setCanceledOnTouchOutside(true)
        Objects.requireNonNull<Window>(dialogInternet.window).setBackgroundDrawableResource(R.color.white)

        dialogInternet.setContentView(R.layout.no_internet_layout)
        dialogInternet.setCancelable(true)
        val tryAgain = dialogInternet.findViewById<TextView>(R.id.tryAgain)


        tryAgain.setOnClickListener {
            tryAgainClick.invoke()
            dialogInternet.dismiss()
        }

        dialogInternet.show()
    }


    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun showAlertDialogSessionExpire(
        header: String?,
        message: String,
        context: Context,
        onConfirmedListener: OnConfirmedListener?
    ) {

        val sessionExpireDialog = Dialog(context)
        sessionExpireDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        sessionExpireDialog.setCanceledOnTouchOutside(false)
        Objects.requireNonNull<Window>(sessionExpireDialog.window)
            .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        sessionExpireDialog.setContentView(R.layout.dialog_one_button)
        sessionExpireDialog.setCancelable(false)
        sessionExpireDialog.show()
        val tvMessage = sessionExpireDialog.findViewById<TextView>(R.id.tv_message)
        val tvHeader = sessionExpireDialog.findViewById<TextView>(R.id.tv_header)
        if (header != null && !header.isEmpty()) {
            tvHeader.visibility = View.VISIBLE
            //tvHeader.text = context.getString(R.string.app_name)
            tvHeader.text = context.getString(R.string.alert_txt)
        } else {
            tvHeader.visibility = View.GONE
        }
        val button_ok = sessionExpireDialog.findViewById<Button>(R.id.button_ok)
        //button_ok.text = context.getString(R.string.login_text)
        tvMessage.text = message
        button_ok.setOnClickListener { view ->
            onConfirmedListener?.onConfirmed()
            sessionExpireDialog.dismiss()
        }
        sessionExpireDialog.show()
    }

    fun showAlertDialog(
        header: String?,
        message: String,
        context: Context,
        onConfirmedListener: OnConfirmedListener?
    ) {

        val alertDialog = Dialog(context)
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        alertDialog.setCanceledOnTouchOutside(false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull<Window>(alertDialog.window)
                .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        alertDialog.setContentView(R.layout.dialog_one_button)
        alertDialog.setCancelable(false)
        // alertDialog.show();
        val tvMessage = alertDialog.findViewById<TextView>(R.id.tv_message)
        val tvHeader = alertDialog.findViewById<TextView>(R.id.tv_header)
        if (header != null && !header.isEmpty()) {
            tvHeader.visibility = View.VISIBLE
            tvHeader.text = header
        } else {
            tvHeader.visibility = View.GONE
            // tvHeader.text = context.getString(R.string.app_name)
        }
        val button_ok = alertDialog.findViewById<Button>(R.id.button_ok)
        tvMessage.text = message
        button_ok.setOnClickListener { view ->
            alertDialog.dismiss()

            if (onConfirmedListener != null) {
                onConfirmedListener.onConfirmed()
                alertDialog.dismiss()
            }
            alertDialog.dismiss()
        }
        if (!alertDialog.isShowing) {
            alertDialog.show()
        }
    }

    fun showOkCancelAlertDialog(
        header: String?,
        message: String,
        context: Context,
        onOkCancelListener: OnOkCancelListener?
    ) {

        val alertDialog = Dialog(context)
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        alertDialog.setCanceledOnTouchOutside(false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull<Window>(alertDialog.window)
                .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        val window = alertDialog.window
        window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        alertDialog.setContentView(R.layout.dialog_two_button)
        alertDialog.setCancelable(false)
        // alertDialog.show();
        val tvMessage = alertDialog.findViewById<TextView>(R.id.tv_message)
        val tvHeader = alertDialog.findViewById<TextView>(R.id.tv_header)
        if (header != null && header.isNotEmpty()) {
            //tvHeader.setVisibility(View.VISIBLE);
            tvHeader.text = header
        } else {
            //tvHeader.setVisibility(View.GONE);
            tvHeader.text = context.getString(R.string.app_name)
        }
        val button_ok = alertDialog.findViewById<TextView>(R.id.buttonYes)
        val button_cancel = alertDialog.findViewById<TextView>(R.id.buttonNo)
        tvMessage.text = message
        button_ok.setOnClickListener { view ->
            if (onOkCancelListener != null) {
                onOkCancelListener.onClickOk()
                alertDialog.dismiss()
            }
            alertDialog.dismiss()
        }
        button_cancel.setOnClickListener { view ->
            if (onOkCancelListener != null) {
                onOkCancelListener.onClickCancel()
                alertDialog.dismiss()
            }
            alertDialog.dismiss()
        }
        if (!alertDialog.isShowing) {
            alertDialog.show()
        }
    }


    fun showAlertDialogWithImage(
        header: String?,
        message: String,
        context: Context,
        onConfirmedListener: OnConfirmedListener?
    ) {

        val alertDialog = Dialog(context)
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        alertDialog.setCanceledOnTouchOutside(false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull<Window>(alertDialog.window)
                .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        alertDialog.setContentView(R.layout.dialog_one_button_image)
        alertDialog.setCancelable(false)
        // alertDialog.show();
        val tvMessage = alertDialog.findViewById<TextView>(R.id.tv_message)
        val tvHeader = alertDialog.findViewById<TextView>(R.id.tv_header)
        if (header != null && !header.isEmpty()) {
            tvHeader.visibility = View.VISIBLE
            tvHeader.text = context.getString(R.string.app_name)
        } else {
            tvHeader.visibility = View.GONE
        }
        val button_ok = alertDialog.findViewById<Button>(R.id.button_ok)
        tvMessage.text = message
        button_ok.setOnClickListener { view ->
            if (onConfirmedListener != null) {
                onConfirmedListener.onConfirmed()
                alertDialog.dismiss()
            }
            alertDialog.dismiss()
        }
        if (!alertDialog.isShowing) {
            alertDialog.show()
        }
    }


    fun showAlertDialogWithoutHeader(
        message: String,
        context: Context,
        onConfirmedListener: OnConfirmedListener?
    ) {

        val alertDialog = Dialog(context)
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        alertDialog.setCanceledOnTouchOutside(false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull<Window>(alertDialog.window)
                .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        alertDialog.setContentView(R.layout.dialog_with_button)
        alertDialog.setCancelable(false)
        // alertDialog.show();
        val tvMessage = alertDialog.findViewById<TextView>(R.id.tv_message)

        val button_ok = alertDialog.findViewById<Button>(R.id.button_ok)
        tvMessage.text = message
        button_ok.setOnClickListener { view ->
            if (onConfirmedListener != null) {
                onConfirmedListener.onConfirmed()
                alertDialog.dismiss()
            }
            alertDialog.dismiss()
        }
        if (!alertDialog.isShowing) {
            alertDialog.show()
        }
    }

    fun showAlertDialog1Button(
        context: Context,
        alertTitle: String,
        alertMsg: String,
        buttonTitle: String,
        onClick: () -> Unit?
    ) {
        val dialog1 = Dialog(context)
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog1.setCanceledOnTouchOutside(false)
        Objects.requireNonNull<Window>(dialog1.window)
            .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog1.setContentView(R.layout.internet_dialog_layout)
        dialog1.setCancelable(false)
        val alertTitleTV = dialog1.findViewById<TextView>(R.id.alertTitle)
        val alertMsgTV = dialog1.findViewById<TextView>(R.id.alertMsg)
        val button = dialog1.findViewById<Button>(R.id.button)
        alertTitleTV.text = alertTitle
        alertMsgTV.text = alertMsg
        button.text = buttonTitle
        button.setOnClickListener {
            onClick.invoke()
            dialog1.dismiss()
        }
        dialog1.show()
    }


    interface OnConfirmedListener {

        fun onConfirmed()


    }


    interface OnOkCancelListener {
        fun onClickOk()
        fun onClickCancel()
    }


}
