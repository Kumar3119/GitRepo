package com.love.githubrepo.ui.base

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import com.love.githubrepo.util.CommonNavigator


import io.reactivex.disposables.CompositeDisposable


abstract class BaseViewModel<N : Any> : ViewModel() {

    private val isLoading = ObservableBoolean(false)
    protected val disposable = CompositeDisposable()

    var navigator: N? = null

    override fun onCleared() {
        super.onCleared()
      //  Logger.e("BaseViewModel", "onCleard -> " + navigator!!.javaClass.simpleName)
        disposable.clear()
    }

    fun setIsLoading(isLoading: Boolean) {
        this.isLoading.set(isLoading)
    }

    fun onBackClick()
    {
        (navigator as CommonNavigator).onBackClick()
    }


}
