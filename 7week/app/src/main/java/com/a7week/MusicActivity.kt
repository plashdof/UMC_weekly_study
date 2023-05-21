package com.a7week

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.a7week.databinding.ActivityMainBinding
import com.a7week.databinding.ActivityMusicBinding

class MusicActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMusicBinding
    private val TAG = "debugging"
    private var mediaPlayer: MediaPlayer? = null
    private var music1state = false
    private var music2state = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMusicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mediaPlayer = MediaPlayer.create(this, R.raw.bgm)

        binding.btnBack.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnPlay.setOnClickListener {
            if(music1state){
                mediaPlayer?.pause()
                binding.btnPlay.setImageResource(R.drawable.play)
                music1state = false
            }else{
                mediaPlayer?.start()
                binding.btnPlay.setImageResource(R.drawable.pause)
                music1state =  true
            }
        }

    }

}