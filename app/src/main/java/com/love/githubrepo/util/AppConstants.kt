package com.love.githubrepo.util

import android.Manifest

object AppConstants {

    val LOG_CAT = "Teengineers"
    val STATUS: String = "status"
    val EMPTY = ""
    val ALERT = "Alert"

    val EXCEPTION_MSG = "Some Exception while printing log :->"

    val BASE_URL = "https://gh-trending-api.herokuapp.com/"
    val INTERNAL_SERVER_ERROR = "Internal Server Error"
    val INPUT_DATE = "yyyy-MM-dd HH:mm:ss"
    val OUTPUT_DATE_FORMAT_WITH_YEAR_24HOUR = "dd MMM yyyy HH:mm"
    val OUTPUT_DATE_FORMAT_WITH_YEAR_12HOUR = "dd MMM yyyy hh:mm a"

    val MEDIA_PERMISSION = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA,
        Manifest.permission.RECORD_AUDIO
    )

    val READ_LOCATION = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    val camera_permissions = arrayOf (
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    )
}
