package com.love.githubrepo

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.love.githubrepo.data.local.AppPreference

class MyApplication : Application() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate() {
        super.onCreate()

        init(this)
        AppPreference.getInstance(this)

    }

    fun init(app: MyApplication) {
        instance = app
    }


    override fun onTerminate() {
        super.onTerminate()
    }


    companion object {
        var instance: MyApplication? = null
            private set
            @Synchronized
            get

//        @Synchronized
//        fun getInstance(): MyApplication {
//            return instance!!
//        }
    }
}

