package com.a9week.search.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.a9week.databinding.RecyclerSearchBinding
import com.a9week.search.models.SearchItems
import com.bumptech.glide.Glide

class SearchAdapter(val context : Context, val searchList : ArrayList<SearchItems>)
    : RecyclerView.Adapter< SearchAdapter.ViewHolder>(){

    inner class ViewHolder(private val viewBinding: RecyclerSearchBinding)
        : RecyclerView.ViewHolder(viewBinding.root){

        fun bind(item : SearchItems){
            Glide.with(itemView)
                .load(item.thumbnail)
                .into(viewBinding.ivPoster)

            if(item.title.length > 15){
                viewBinding.tvTitle.text = item.title.substring(0,11)
            }else{
                viewBinding.tvTitle.text = item.title
            }

            viewBinding.tvInfo.text  = item.title


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : ViewHolder {
        val viewBinding = RecyclerSearchBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(searchList[position])
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

}