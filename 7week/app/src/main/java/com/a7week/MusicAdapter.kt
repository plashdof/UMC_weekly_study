package com.a7week

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a7week.databinding.RecyclerSingBinding

class MusicAdapter(val context : Context, val datas : Array<MusicData>, val link : MusicActivity.RoomToAdapter)
    :RecyclerView.Adapter<MusicAdapter.ViewHolder>(){

    inner class ViewHolder(private val viewBinding: RecyclerSingBinding)
        : RecyclerView.ViewHolder(viewBinding.root){

        fun bind(item : MusicData){
            viewBinding.ivImage.setImageResource(item.poster)
            viewBinding.tvTitle.text = item.title
            viewBinding.tvArtist.text = item.artist
            viewBinding.layout.setOnClickListener {
                link.MusicClicked(item)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : ViewHolder {
        val viewBinding = RecyclerSingBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int {
        return datas.size
    }

}