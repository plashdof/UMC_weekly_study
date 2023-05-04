package com.a4week

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.a4week.databinding.ActivityDialogBinding

class DialogActivity  : AppCompatActivity(){

    private lateinit var binding  : ActivityDialogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnYes.setOnClickListener {
            Singleton.answer = "yes"
            finish()
        }
        binding.btnNo.setOnClickListener {
            Singleton.answer="no"
            finish()
        }

    }
}