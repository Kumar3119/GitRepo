package com.love.githubrepo.util

import org.json.JSONObject

interface NetworkResponseCallback<T> {

    fun onResponse(`object`: T)

    fun onFailure(message: String)

    fun onServerError(error: String)

    fun onSessionExpire(error: String)

    fun onAppVersionUpdate(msg: String)

    fun onErrorMessage (errorMessage :String){}

    open fun navigateToOtp(msg: String) {}

    fun appModeChange(checkJsonErrorBody: JSONObject)
}
