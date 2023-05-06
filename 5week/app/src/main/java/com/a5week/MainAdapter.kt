package com.a5week

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.a5week.databinding.RecyclerItemBinding

class MainAdapter(val context: Context, private val datas: Array<MemoData>, private val link : MainActivity.RoomToAcitivity)
    : RecyclerView.Adapter<MainAdapter.ViewHolder>(){

    val TAG = "debugging"

    inner class ViewHolder(private val viewBinding: RecyclerItemBinding)
        : RecyclerView.ViewHolder(viewBinding.root){

        @SuppressLint("ClickableViewAccessibility", "ResourceAsColor")
        fun bind(item : MemoData){
            viewBinding.tv.text = item.text
            viewBinding.tvTitle.text = item.title
            viewBinding.tvDate.text = item.date
            viewBinding.tvTime.text = item.time

            viewBinding.cv.setOnTouchListener { _, event ->
                when (event.actionMasked) {
                    MotionEvent.ACTION_DOWN -> {
                        Log.d(TAG,"ACTION DOWN")
                        viewBinding.cv.alpha = 0.8F
                        false
                    }
                    MotionEvent.ACTION_UP -> {
                        Log.d(TAG,"ACTION UP")
                        viewBinding.cv.alpha = 1.0F
                        false
                    }
                    else -> false
                }
            }

            // 메모 짧게 클릭시 수정화면 이동
            viewBinding.cv.setOnClickListener {
                Log.d(TAG,"l")
                val intent = Intent(context,MakememoActivity::class.java)
                    .putExtra("title", item.title)
                    .putExtra("text",item.text)
                    .putExtra("position",adapterPosition.toString())
                context.startActivity(intent)
            }

            viewBinding.cv.setOnLongClickListener {
                Log.d(TAG,"ll")
                link.deleteStart()
                true
            }

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