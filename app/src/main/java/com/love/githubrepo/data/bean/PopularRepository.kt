package com.love.githubrepo.data.bean

import com.google.gson.annotations.SerializedName


data class PopularRepository (

    @SerializedName("repositoryName" ) var repositoryName : String? = null,
    @SerializedName("description"    ) var description    : String? = null,
    @SerializedName("url"            ) var url            : String? = null

)
