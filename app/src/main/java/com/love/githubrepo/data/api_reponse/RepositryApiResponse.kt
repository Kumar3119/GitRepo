package com.love.githubrepo.data.api_reponse

import Api
import com.google.gson.annotations.SerializedName
import com.love.githubrepo.data.Parser
import com.love.githubrepo.data.bean.Repositry
import com.love.githubrepo.data.local.AppPreference
import com.love.githubrepo.data.local.PreferenceKeys
import com.love.githubrepo.data.remote.ApiFactory
import com.love.githubrepo.ui.base.BaseResponse
import com.love.githubrepo.util.AppConstants
import com.love.githubrepo.util.NetworkResponseCallback
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.HashMap

class RepositryApiResponse : BaseResponse<ArrayList<Repositry>, String, Any>() {
 //   val data: ArrayList<Repositry>? = null

    override fun doNetworkRequest(
        requestParam: HashMap<String, Any>,
        apppref: AppPreference,
        networkResponseCallback: NetworkResponseCallback<ArrayList<Repositry>>
    ): Disposable {
        val api = ApiFactory.getClientWithHeader(
            AppConstants.BASE_URL).create(Api::class.java)
        return api.getDevelopers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(networkResponseCallback::onResponse) { throwable ->
                Parser.parseErrorResponse(
                    throwable,
                    networkResponseCallback
                )
            }
    }

}