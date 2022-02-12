package com.love.githubrepo.data.bean

import com.google.gson.annotations.SerializedName

data class Repositry(
    @SerializedName("rank"              ) var rank              : Int?               = null,
    @SerializedName("username"          ) var username          : String?            = null,
    @SerializedName("name"              ) var name              : String?            = null,
    @SerializedName("url"               ) var url               : String?            = null,
    @SerializedName("avatar"            ) var avatar            : String?            = null,
    @SerializedName("since"             ) var since             : String?            = null,
    @SerializedName("popularRepository" ) var popularRepository : PopularRepository? = PopularRepository(),
     var isSelect : Boolean? = false
)
