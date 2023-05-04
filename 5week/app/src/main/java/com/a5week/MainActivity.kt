package com.a5week

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.a5week.R
import com.a5week.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = listOf(R.drawable.pikachu, R.drawable.piri, R.drawable.ggobuk,R.drawable.pikachu,
            R.drawable.piri, R.drawable.ggobuk,R.drawable.pikachu, R.drawable.piri, R.drawable.ggobuk,R.drawable.pikachu,
            R.drawable.piri, R.drawable.ggobuk,R.drawable.pikachu, R.drawable.piri, R.drawable.ggobuk,R.drawable.pikachu,
            R.drawable.piri, R.drawable.ggobuk,)


        val adapter = MainAdapter(this, data)
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(this)
    }

}