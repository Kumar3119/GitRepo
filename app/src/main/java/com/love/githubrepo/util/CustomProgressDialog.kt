package com.love.githubrepo.util

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.love.githubrepo.R
import com.wang.avi.AVLoadingIndicatorView


class CustomProgressDialog : Dialog {
    var mContext: Context? = null

    constructor(context: Context) : super(context) {
        this.mContext = context
    }

    constructor(context: Context, theme: Int) : super(context, theme)


    fun hideProgressDialog() {
        try {
            if (pDialog != null) {
                if (pDialog!!.isShowing)
                    pDialog!!.dismiss()
                pDialog = null
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun showProgressDialog(message: String) {
        try {
            if (pDialog == null) {
                pDialog = createProgressDialog(context, false, message)
                pDialog!!.setCanceledOnTouchOutside(false)
                pDialog!!.show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun createProgressDialog(
        context: Context,
        cancelable: Boolean,
        message: String
    ): CustomProgressDialog {

        val dialog = CustomProgressDialog(context, R.style.CustomProgressDialog)
        dialog.setTitle("")
        dialog.setContentView(R.layout.dialog_custome_progress)
        dialog.setCancelable(cancelable)
        dialog.setCanceledOnTouchOutside(cancelable)
        val textOfLoader = dialog.findViewById<TextView>(R.id.textOfLoader)
        val loader = dialog.findViewById<AVLoadingIndicatorView>(R.id.avi)
        textOfLoader.text = message
        dialog.window!!.attributes.gravity = Gravity.CENTER
        val lp = dialog.window!!.attributes
        lp.dimAmount = 0.4f
        dialog.window!!.attributes = lp
        val a = AnimationUtils.loadAnimation(getContext(), R.anim.progress_anim)
        a.duration = 1000
        loader.startAnimation(a)

        return dialog
    }

    fun setMessage(message: CharSequence?) {
        if (message != null && message.isNotEmpty()) {
            val txt = findViewById<TextView>(R.id.textOfLoader)
            txt.text = message
            txt.invalidate()
        }
    }

    companion object {
        private var pDialog: Dialog? = null
    }
}

