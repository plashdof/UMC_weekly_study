package com.a9week

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.a9week.movie.MovieFragment
import com.a9week.databinding.ActivityHomeBinding
import com.a9week.profile.ProfileFragment
import com.a9week.search.SearchFragment
import com.a9week.ticket.TicketFragment

class HomeActivity : AppCompatActivity() {

    val TAG = "debugging"
    private lateinit var binding : ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNV.run {
            setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.movie_btn -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.frame, MovieFragment())
                            .addToBackStack(null)
                            .commitAllowingStateLoss()
                    }

                    R.id.search_btn -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.frame, SearchFragment())
                            .addToBackStack(null)
                            .commitAllowingStateLoss()
                    }

                    R.id.ticket_btn -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.frame, TicketFragment())
                            .addToBackStack(null)
                            .commitAllowingStateLoss()
                    }

                    R.id.profile_btn -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.frame, ProfileFragment())
                            .addToBackStack(null)
                            .commitAllowingStateLoss()
                    }
                }
                true
            }
            selectedItemId = R.id.movie_btn
        }







    }



}