package com.week2.a3week

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.week2.a3week.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySecondBinding
    private val RC_OK = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("aaaaa","onCreate")

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.hasExtra("text")){
            binding.tvMain.text = intent.getStringExtra("text").toString()
        }

        binding.btnMain.setOnClickListener {
            val intent = Intent(this, ThirdActivity::class.java)
            startActivity(intent)
        }

        val intent = Intent(this, MainActivity::class.java)
            .putExtra("back","Back")
        setResult(RC_OK, intent)



        binding.btnBack.setOnClickListener {
            finish()
        }
    }

}