package com.love.githubrepo.ui.home

import com.love.githubrepo.data.bean.Repositry
import com.love.githubrepo.util.CommonNavigator
import java.util.ArrayList

interface HomeNavigator : CommonNavigator {
    fun setData(data: ArrayList<Repositry>)
}