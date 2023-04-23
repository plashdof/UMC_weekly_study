package com.week2.a3week

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.week2.a3week.databinding.ActivityThirdBinding

class ThirdActivity : AppCompatActivity() {

    private lateinit var binding : ActivityThirdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnFrag1.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .add(R.id.frag_main,FirstFragment())
                .commit()

        }

        binding.btnFrag2.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .add(R.id.frag_main,SecondFragment())
                .commit()
        }

        binding.btnFrag3.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .add(R.id.frag_main,ThirdFragment())
                .commit()
        }

    }

    fun receiveText(text : String){
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show()
        binding.tvText.text = text
    }
}