package com.week2.instahome

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.week2.instahome.adapter.PostAdapter
import com.week2.instahome.adapter.StoryThumbnailAdapter
import com.week2.instahome.databinding.FragmentHomeBinding
import com.week2.instahome.models.PostData

class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val data1 =
        PostData(R.drawable.profile1, "noah","좋아요 5개","안녕하세요","노아입니다","#노아","댓글 10개 모두 보기", "04월 07일",
            arrayOf(R.drawable.image1,R.drawable.image2))
    private val data2 =
        PostData(R.drawable.profile2, "kim","좋아요 15개","ㅎㅇ","kim입니다","#Kim","댓글 2개 모두 보기", "03월 07일",
            arrayOf(R.drawable.image3,R.drawable.image4))
    private val data3 =
        PostData(R.drawable.profile3, "Topgun","좋아요 7개","Hello","Topgun입니다","#Topgun","댓글 3개 모두 보기", "03월 25일",
            arrayOf(R.drawable.image5,R.drawable.image6))
    private val data4 =
        PostData(R.drawable.profile4, "Maburik","좋아요 8개","Hi","Maburik입니다","#Maburik","댓글 4개 모두 보기", "02월 20일",
            arrayOf(R.drawable.image1,R.drawable.image2,R.drawable.image3))
    private val data5 =
        PostData(R.drawable.profile5, "Stan","좋아요 9개","반갑습니다","Stan입니다","#Stan","댓글 11개 모두 보기", "04월 05일",
            arrayOf(R.drawable.image1,R.drawable.image2,R.drawable.image8))
    private val data6 =
        PostData(R.drawable.profile1, "Mopangju","좋아요 3개","안녕하세요","Mopangju입니다","#Mopanju","댓글 12개 모두 보기", "04월 01일",
            arrayOf(R.drawable.image1,R.drawable.image2,R.drawable.image7))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val storyThumbnailDatas = arrayOf(R.drawable.profile1, R.drawable.profile2, R.drawable.profile3, R.drawable.profile4, R.drawable.profile5,
            R.drawable.profile1, R.drawable.profile2, R.drawable.profile3, R.drawable.profile4, R.drawable.profile5)

        val storyAdapter = StoryThumbnailAdapter(App.context(),storyThumbnailDatas)
        binding.recyclerHomeStory.adapter = storyAdapter
        binding.recyclerHomeStory.layoutManager = LinearLayoutManager(App.context(),RecyclerView.HORIZONTAL,false)

        val postDatas = arrayOf(data1,data2,data3,data4,data5,data1)

        val bodyAdapter = PostAdapter(App.context(),postDatas)
        binding.recyclerHomeBody.adapter = bodyAdapter
        binding.recyclerHomeBody.layoutManager = LinearLayoutManager(App.context(),RecyclerView.VERTICAL,false)
    }
}