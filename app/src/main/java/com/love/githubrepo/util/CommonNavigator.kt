package com.love.githubrepo.util

import android.app.Activity
import com.love.githubrepo.R
import org.json.JSONObject


interface CommonNavigator {

    fun init()

    fun showProgress()

    fun hideProgress()

    fun showServerError(error: String)

    fun showAppUpdateError(error: String)

    fun showNetworkError(error: String)

    fun showSessionExpireAlert(error: String, activity: Activity)

    fun showValidationError(message: String)

    fun setRootViewVisibility()

    fun getStringResource(id: Int): String

    fun getIntegerResource(id: Int): Int

    fun setStatusBarColor() {}

    fun showAlertDialog1Button(alertTitle: String = getStringResource(R.string.dialog_alert_heading), alertMsg: String, buttonTitle: String = getStringResource(R.string.ok), onClick: () -> Unit? = {})

    fun showNetworkError(error:String,isRedirect : Boolean){}

    fun onBackClick()
}
