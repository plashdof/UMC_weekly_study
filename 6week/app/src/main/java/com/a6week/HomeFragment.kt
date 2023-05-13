package com.a6week

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.a6week.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    var tabTextList = arrayOf("Tab1", "Tab2", "Tab3")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPagerAdapter = ViewPageFSAdapter(requireActivity())
        binding.viewpage.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tab, binding.viewpage){
            // tab, position -> 적용하고 싶은 이벤트 or 명령들 작성
                tab, position -> tab.text = tabTextList[position]
        }.attach()
    }

}