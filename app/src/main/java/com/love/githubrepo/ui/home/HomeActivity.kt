package com.love.githubrepo.ui.home

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.love.githubrepo.BR
import com.love.githubrepo.R
import com.love.githubrepo.data.bean.Repositry
import com.love.githubrepo.data.local.PreferenceKeys
import com.love.githubrepo.databinding.ActivityHomeBinding
import com.love.githubrepo.ui.base.BaseActivity
import com.love.githubrepo.ui.home.adapter.RepositryAdapter
import java.util.ArrayList

class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>() , HomeNavigator {

    var adapter : RepositryAdapter? = null
    override val bindingVariable: Int
        get() = BR.homeVM
    override val layoutId: Int
        get() = R.layout.activity_home
    override val viewModel: HomeViewModel
        get() = ViewModelProvider(this).get(HomeViewModel::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.navigator = this;
        if(savedInstanceState==null) {
            init()
        }
    }


    override fun setData(data: ArrayList<Repositry>) {
        viewModel.repositryList.clear()
        viewModel.repositryList.addAll(data)
        adapter!!.notifyDataSetChanged()

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val gson = Gson()
        val json = gson.toJson(viewModel.repositryList)
        outState.putString(PreferenceKeys.REPOSITRY_DATA.key, json)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

      //  val myString = savedInstanceState.getString(PreferenceKeys.REPOSITRY_DATA.key)

        if (savedInstanceState != null) {
            val json = savedInstanceState.getString(PreferenceKeys.REPOSITRY_DATA.key)
            if (!json!!.isEmpty()) {
                val gson = Gson()
                val itemType = object : TypeToken<ArrayList<Repositry>>() {}.type
                viewModel.repositryList.clear()
                viewModel.repositryList.addAll(gson.fromJson<ArrayList<Repositry>>(json, itemType))
              setInital()
            }
        }
    }

    override fun init() {
        if (checkIfInternetOn()) {
            viewModel.getRepos()
            //  viewModel.getProjectWiseBalance()
        }
        setInital()

    }

    fun setInital(){
        adapter = RepositryAdapter(
            this,
            viewModel.repositryList!!,
            object : RepositryAdapter.OnItemClickListener {
                override fun onRowClicked(position: Int) {
                    if (viewModel.repositryList!![position].isSelect!!)
                        viewModel.repositryList!![position].isSelect = false
                    else
                        viewModel.repositryList!![position].isSelect = true

                    adapter!!.notifyDataSetChanged()
                }
            })
        viewDataBinding!!.rvBalanceList.adapter = adapter
        viewDataBinding!!.repoSearch.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (adapter!=null)
                    adapter!!.filter.filter(newText)
                return false
            }

        })
    }

}


