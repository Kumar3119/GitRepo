package com.love.githubrepo.data.local

import android.content.Context
import android.content.SharedPreferences
import com.love.githubrepo.MyApplication
import com.love.githubrepo.util.CommonUtils


class SharedPreferenceManager {

    val TAG = "" + SharedPreferenceManager::class.java.simpleName
    val CURRENT_USER_INFO_PREFS = "current_user_info"


    private var sharedPreferences: SharedPreferences? = null

    constructor() {
        instance = this
        if (MyApplication.instance != null) {
            sharedPreferences = MyApplication.instance!!.getSharedPreferences(
                CURRENT_USER_INFO_PREFS,
                Context.MODE_PRIVATE
            )
        }
    }

    companion object {
        private var instance: SharedPreferenceManager? = null
        @Synchronized
        fun getInstance(): SharedPreferenceManager {
            if (instance == null) {
                instance = SharedPreferenceManager()
            }
            return instance as SharedPreferenceManager
        }
    }


    @Suppress("UNCHECKED_CAST")
    operator fun <T> get(key: String, defValue: T): T {
        val returnValue = sharedPreferences!!.getAll()[key] as T?
        return returnValue ?: defValue
    }

    fun has(key: String): Boolean {
        return sharedPreferences!!.contains(key)
    }

    private fun getEditor(): SharedPreferences.Editor {
        return sharedPreferences!!.edit()
    }


    fun delete(key: String) {
        if (sharedPreferences!!.contains(key)) {
            getEditor().remove(key).commit()
        }
    }

}