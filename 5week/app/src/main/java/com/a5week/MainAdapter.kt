package com.a5week

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a5week.databinding.RecyclerItemBinding

class MainAdapter(val context : Context, private val datas : List<Int>)
    : RecyclerView.Adapter<MainAdapter.ViewHolder>(){

    // check 표시여부 저장할 데이터사이즈와 동일한 크기의 배열 생성.
    var checkarr : Array<Boolean> = Array(datas.size){false}

    inner class ViewHolder(private val viewBinding: RecyclerItemBinding)
        : RecyclerView.ViewHolder(viewBinding.root){
        fun bind(item : Int){
            viewBinding.iv.setImageResource(item)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : ViewHolder {
        val viewBinding = RecyclerItemBinding
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