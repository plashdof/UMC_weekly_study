package com.a6week

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.a6week.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    var tabTextList = arrayOf("tab1","tab2","tab3")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                }

                true
            }
            selectedItemId = R.id.menu_btn_home
        }

    }

}