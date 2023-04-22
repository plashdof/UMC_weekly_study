package com.week2.instahome.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.week2.instahome.databinding.RecyclerStoryThumbnailBinding

class StoryThumbnailAdapter(val context : Context, val img : Array<Int>) : RecyclerView.Adapter<StoryThumbnailAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding : RecyclerStoryThumbnailBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(item : Int){
            binding.recyclerStorythumbnailImagebtn.setImageResource(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerStoryThumbnailBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(img[position])
    }

    override fun getItemCount(): Int {
        return img.size
    }
}