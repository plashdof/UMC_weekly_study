package com.week2.instahome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.week2.instahome.databinding.FragmentShoppingBinding

class ShoppingFragment : Fragment() {

    private var _binding : FragmentShoppingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentShoppingBinding.inflate(inflater, container, false)
        return binding.root


    }
}