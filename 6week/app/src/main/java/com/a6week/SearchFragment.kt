package com.a6week

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.a6week.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private var _binding : FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val sliderImageHandler: Handler = Handler()
    private val sliderImageRunnable = Runnable { binding.viewpage.currentItem = binding.viewpage.currentItem + 1 }


    var imgList = arrayOf(R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image5)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ViewPageRecyclerAdapter(imgList)
        binding.viewpage.adapter = adapter
        binding.indicator.setViewPager(binding.viewpage)

        binding.viewpage.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                sliderImageHandler.removeCallbacks(sliderImageRunnable)
                //자동 스크롤은 5초 당 한 번 넘어가도록
                sliderImageHandler.postDelayed(sliderImageRunnable, 3000)
            }
        })
    }

}