package com.love.githubrepo.ui.home

import com.love.githubrepo.data.api_reponse.RepositryApiResponse
import com.love.githubrepo.data.bean.Repositry
import com.love.githubrepo.data.local.AppPreference
import com.love.githubrepo.ui.base.BaseViewModel
import com.love.githubrepo.util.NetworkResponseCallback
import org.json.JSONObject

class HomeViewModel : BaseViewModel<HomeNavigator>() {

    var repositryList = ArrayList<Repositry>()
    fun getRepos() {

          navigator!!.showProgress()

        disposable.add(
            RepositryApiResponse().doNetworkRequest(
                HashMap(), AppPreference,
                object : NetworkResponseCallback<ArrayList<Repositry>> {

                    override fun onResponse(reponse: ArrayList<Repositry>) {
                        navigator!!.hideProgress()


                                navigator!!.setData(reponse!!)



                    }

                    override fun onFailure(message: String) {
                        navigator!!.hideProgress()
                        navigator!!.showNetworkError(message)
                    }

                    override fun onServerError(error: String) {
                        navigator!!.hideProgress()
                        navigator!!.showNetworkError(error)
                    }

                    override fun onErrorMessage(errorMessage: String) {
                        navigator!!.hideProgress()
                        super.onErrorMessage(errorMessage)
                        navigator!!.showNetworkError(errorMessage, false)
                    }

                    override fun onSessionExpire(error: String) {
                        navigator!!.hideProgress()
                    }

                    override fun onAppVersionUpdate(msg: String) {
                        navigator!!.hideProgress()
                        navigator!!.showNetworkError(msg)
                    }

                    override fun appModeChange(checkJsonErrorBody: JSONObject) {
                        navigator!!.hideProgress()

                    }
                })
        )
    }
}