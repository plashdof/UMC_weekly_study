package com.week2.instahome

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.week2.instahome.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainBtmNav.itemIconTintList = null

        binding.mainBtmNav.run{
            setOnItemSelectedListener { item->
                when(item.itemId){
                    R.id.menu_btn_home -> {
                        Log.d("ddddd","Homeclicked")
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, HomeFragment())
                            .addToBackStack(null)
                            .commitAllowingStateLoss()
                    }
                    R.id.menu_btn_profile -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, ProfileFragment())
                            .addToBackStack(null)
                            .commitAllowingStateLoss()
                    }
                    R.id.menu_btn_search -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, SearchFragment())
                            .addToBackStack(null)
                            .commitAllowingStateLoss()
                    }
                    R.id.menu_btn_video -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, VideoFragment())
                            .addToBackStack(null)
                            .commitAllowingStateLoss()
                    }
                    R.id.menu_btn_shopping ->{
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, ShoppingFragment())
                            .addToBackStack(null)
                            .commitAllowingStateLoss()
                    }
                }

                true
            }
            selectedItemId = R.id.menu_btn_home
        }


    }
}