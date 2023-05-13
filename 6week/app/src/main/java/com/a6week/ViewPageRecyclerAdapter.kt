package com.a6week

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a6week.databinding.ViewpageItemBinding

class ViewPageRecyclerAdapter(val datas : Array<Int>) : RecyclerView.Adapter<ViewPageRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding : ViewpageItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item : Int){
            binding.iv.setImageResource(item)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewpageItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = datas.size

}