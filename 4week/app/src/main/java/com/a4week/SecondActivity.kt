package com.a4week

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.a4week.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity(){

    private val TAG = "debugging"
    private lateinit var binding : ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Singleton.answer = ""

        if(intent.hasExtra("text")){
            binding.tvMain.text = intent.getStringExtra("text")
        }

        binding.btnMain.setOnClickListener {
            finish()
        }


    }

}