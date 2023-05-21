package com.a7week

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.a7week.databinding.FragmentBottomsheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetDialog : BottomSheetDialogFragment() {

    private var _binding : FragmentBottomsheetBinding? =null
    private val binding get() = _binding!!
    private val TAG = "debugging"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBottomsheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        
        // 각 Edit Text Focus 되면, Warning 메세지 사라지게 하기

        binding.etHour.setOnFocusChangeListener { view, hasFocus ->
            if(hasFocus){
                binding.tvWarning.visibility = View.GONE
            }
        }

        binding.etMinute.setOnFocusChangeListener { view, hasFocus ->
            if(hasFocus){
                binding.tvWarning.visibility = View.GONE
            }
        }

        binding.etSecond.setOnFocusChangeListener { view, hasFocus ->
            if(hasFocus){
                binding.tvWarning.visibility = View.GONE
            }
        }

        binding.btnStart.setOnClickListener {
            var stringHour = binding.etHour.text.toString()
            var stringMinute = binding.etMinute.text.toString()
            var stringSecond = binding.etSecond.text.toString()

            Log.d(TAG, "$stringHour $stringMinute $stringSecond")

            if(stringHour.isBlank() && stringMinute.isBlank() && stringSecond.isBlank()){
                
                // 아무것도 입력하지 않았으면, Warning 메세지 보여줌
                binding.tvWarning.visibility = View.VISIBLE

            }else{
                
                // 각 입력값이 빈칸이 아닐경우만, Singleton 에 값 저장
                if(stringHour.isNotBlank()) SingleTon.hours = stringHour.toInt()
                if(stringMinute.isNotBlank()) SingleTon.minutes = stringMinute.toInt()
                if(stringSecond.isNotBlank()) SingleTon.seconds = stringSecond.toInt()

                val intent = Intent(requireContext(), MainActivity::class.java)
                    .putExtra("startThread","true")
                startActivity(intent)
            }

        }
    }
}