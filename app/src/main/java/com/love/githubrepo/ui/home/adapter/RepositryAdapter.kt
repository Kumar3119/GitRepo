package com.love.githubrepo.ui.home.adapter

import android.content.Context
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.love.githubrepo.R
import com.love.githubrepo.data.bean.Repositry
import com.love.githubrepo.databinding.RowReposBinding
import com.love.githubrepo.ui.base.BaseRecyclerAdapter
import java.util.*
import kotlin.collections.ArrayList

class RepositryAdapter(
    private val context: Context,
    val repositryList: ArrayList<Repositry>,
    val mOnItemClickListener : OnItemClickListener
) :
    BaseRecyclerAdapter<RowReposBinding, Any, RepositryAdapter.ViewHolder>(), Filterable {

    var repositryFilterList = ArrayList<Repositry>()

    init {
        repositryFilterList = repositryList
    }
    interface OnItemClickListener {
        fun onRowClicked(position: Int)
    }


    override fun onCreateViewHolder(
        viewDataBinding: RowReposBinding,
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(viewDataBinding)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ViewHolder, position: Int, type: Int) {
        holder.bindToDataVM(holder.bindingVariable, holder.viewModel)
        holder.bindTo(position)
        if (repositryFilterList!![position].isSelect!!){
            holder.viewDataBinding.ivCheck.visibility = View.VISIBLE
        }else
            holder.viewDataBinding.ivCheck.visibility = View.GONE
        holder.viewDataBinding.tvName.text = repositryFilterList!![position].name
        holder.viewDataBinding.tvRank.text = repositryFilterList!![position].rank.toString()
        if(repositryFilterList!![position].popularRepository!=null){
            holder.viewDataBinding.tvRepo.text = repositryFilterList!![position].popularRepository!!.repositoryName
        }
        else{
            holder.viewDataBinding.tvRepo.text = "NA"
        }

        Glide.with(context)
            .load(repositryFilterList!![position].avatar)
            .transform(CircleCrop())
            .into(holder.viewDataBinding.profileImage)

        holder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                   if (mOnItemClickListener!=null)
                       mOnItemClickListener.onRowClicked(position)
                // Do some work here
            }

        })

    }

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.row_repos
    }

    override fun getItemCount(): Int {
        return repositryFilterList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ViewHolder(mViewDataBinding: RowReposBinding) :
        BaseViewHolder(mViewDataBinding) {

        override val viewModel: Any
            get() = Any()

        override val bindingVariable: Int
            get() = 0

    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    repositryFilterList = repositryList
                } else {
                    val resultList = ArrayList<Repositry>()
                    for (row in repositryList) {

                        if ( row.name!!.lowercase(Locale.ROOT).contains(charSearch.lowercase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    repositryFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = repositryFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                repositryFilterList = results?.values as ArrayList<Repositry>
                notifyDataSetChanged()
            }

        }
    }


}