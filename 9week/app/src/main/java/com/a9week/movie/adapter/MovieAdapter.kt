package com.a9week.movie.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a9week.databinding.RecyclerMovieBinding
import com.a9week.movie.models.MovieBoxRankings

class MovieAdapter(val context : Context, val movieList : ArrayList<MovieBoxRankings>)
    : RecyclerView.Adapter< MovieAdapter.ViewHolder>(){

    inner class ViewHolder(private val viewBinding: RecyclerMovieBinding)
        : RecyclerView.ViewHolder(viewBinding.root){

        fun bind(item : MovieBoxRankings){
            viewBinding.tvRank.text = item.rank
            viewBinding.tvName.text = item.movieNm
            viewBinding.tvAud.text = "누적 : " + item.audiAcc
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : ViewHolder {
        val viewBinding = RecyclerMovieBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(movieList[position])
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

}