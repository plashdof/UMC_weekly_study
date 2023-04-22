package com.week2.instahome.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.week2.instahome.databinding.ViewpagePostBinding

class ViewpageAdapter(val datas : Array<Int>) : RecyclerView.Adapter<ViewpageAdapter.ViewHolder>() {


    inner class ViewHolder(private val binding : ViewpagePostBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item : Int){
            binding.ivImage.setImageResource(item)
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewpagePostBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

}