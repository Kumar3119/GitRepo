package com.love.githubrepo.util

import com.google.gson.annotations.SerializedName


data class ErrorBean(
    @SerializedName("message")
    val message: String?
)
