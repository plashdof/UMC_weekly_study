package com.week2.instahome.adapter

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.week2.instahome.databinding.RecyclerPostBinding
import com.week2.instahome.models.PostData

class PostAdapter(context : Context, val datas : Array<PostData>) : RecyclerView.Adapter<PostAdapter.ViewHolder>(){

    inner class ViewHolder(private val binding : RecyclerPostBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item : PostData){
            binding.ivProfile.setImageResource(item.profileImg)
            binding.tvLikeCount.text = item.likeCount
            binding.tvCommentCount.text = item.commentCount
            binding.tvBottomNick.text = item.profileName
            binding.tvTopNick.text = item.profileName
            binding.tvDate.text = item.date

            binding.tvFirstText.text = item.firstText
            binding.tvSecondText.text = item.secondText


            // viewpager 과 indicator 연결

            val indicator = binding.indicator
            indicator.noOfPages = item.imgList.size

            val adapter = ViewpageAdapter(item.imgList)
            binding.viewpager.adapter = adapter

            binding.viewpager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                }

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    indicator.onPageChange(position)
                }

                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                }
            })


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerPostBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int {
        return datas.size
    }

}