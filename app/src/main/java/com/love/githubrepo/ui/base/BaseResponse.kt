package com.love.githubrepo.ui.base

import com.google.gson.annotations.SerializedName
import com.love.githubrepo.data.local.AppPreference
import com.love.githubrepo.util.ErrorBean
import com.love.githubrepo.util.NetworkResponseCallback
import io.reactivex.disposables.Disposable
import java.util.*

abstract class BaseResponse<T, K, V> {

    @SerializedName("error")
    var error: Boolean = false

    @SerializedName("statusCode")
    var statusCode: Int = 0

    @SerializedName("message")
    var message: String = ""


    /**
     * This method is used to call api and handel response.
     *
     * @param HashMap contains values to send in api.
     * @param  vararg variable arguments can accept arbitrary number of values.
     * @param NetworkResponseCallback method which handel api response.
     */
    abstract fun doNetworkRequest(requestParam: HashMap<K, V>, apppref: AppPreference, networkResponseCallback: NetworkResponseCallback<T>): Disposable

}
