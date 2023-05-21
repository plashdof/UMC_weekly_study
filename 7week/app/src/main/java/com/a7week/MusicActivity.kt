package com.a7week

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.a7week.databinding.ActivityMainBinding
import com.a7week.databinding.ActivityMusicBinding
import java.util.concurrent.TimeUnit

class MusicActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMusicBinding
    private val TAG = "debugging"
    private lateinit var mediaPlayer: MediaPlayer
    private var state = false
    private var mediaFileLength : Int = 0
    private val handler = Handler()

    private var minutes = 0
    private var seconds = 0
    private var duration : Long = 0
    private var threadState = true
    inner class RoomToAdapter(){
        fun MusicClicked(music : MusicData){


            runOnUiThread {
                threadState = false
                binding.tvTitle.text  = music.title
                binding.tvArtist.text  = music.artist
                binding.ivImage.setImageResource(music.poster)
                mediaPlayer = MediaPlayer.create(this@MusicActivity, music.music)

                mediaPlayer.setOnPreparedListener{
                    Log.d(TAG,"setonPrepared")
                    duration = mediaPlayer.duration.toLong()
                    mediaFileLength = mediaPlayer.duration
                    binding.seekBar.max = mediaFileLength
                    setTimeText()
                    binding.seekBar.progress = 0
                }

                binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
                    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                        if(fromUser){
                            mediaPlayer.seekTo(progress)
                            duration = progress.toLong()
                            setTimeText()
                        }
                    }
                    override fun onStartTrackingTouch(seekBar: SeekBar?) {
                        mediaPlayer.pause()
                        binding.btnPlay.setImageResource(R.drawable.play)
                        state = false
                    }
                    override fun onStopTrackingTouch(seekBar: SeekBar?) {
                        mediaPlayer.start()
                        binding.btnPlay.setImageResource(R.drawable.pause)
                        updateSeekBar()
                        state = true
                    }
                })

                binding.btnPlay.setOnClickListener {
                    state = if(state){
                        mediaPlayer.pause()
                        binding.btnPlay.setImageResource(R.drawable.play)
                        false
                    }else{
                        mediaPlayer.start()
                        threadState = true
                        startThread()
                        binding.btnPlay.setImageResource(R.drawable.pause)
                        updateSeekBar()
                        true
                    }
                }
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMusicBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnBack.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
        val data1 = MusicData("Papi","Yarin Primak",R.drawable.poster1,R.raw.music1)
        val data2 = MusicData("Horizons","Wonderland",R.drawable.poster2,R.raw.music2)
        val data3 = MusicData("I Just Wanna Have Fun","Flint",R.drawable.poster3,R.raw.music3)
        val data4 = MusicData("Breaking Sweat","BallonPlanet",R.drawable.poster4,R.raw.music4)
        val datas = arrayOf(data1, data2, data3, data4)
        val adapter = MusicAdapter(this, datas, RoomToAdapter())
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(this)

        RoomToAdapter().MusicClicked(data1)

    }

    private fun updateSeekBar() {
        binding.seekBar.progress = mediaPlayer.currentPosition
        if (mediaPlayer.isPlaying) {
            handler.postDelayed({ updateSeekBar() }, 1000)
        }
    }

    private fun setTimeText(){
        runOnUiThread {
            minutes = TimeUnit.MILLISECONDS.toMinutes(duration).toInt()
            seconds = TimeUnit.MILLISECONDS.toSeconds(duration).toInt() % 60
            var secondsString = ""
            var minutesString = ""
            if(seconds < 10) secondsString = "0$seconds"
            else secondsString = "$seconds"
            if(minutes < 10) minutesString = "0$minutes:"
            else minutesString = "$minutes"
            binding.tvRunningTime.text = minutesString + secondsString
        }
    }

    private fun startThread(){
        Thread(){
            while(threadState){
                duration = binding.seekBar.progress.toLong()
                setTimeText()
                Thread.sleep(10)
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
        handler.removeCallbacksAndMessages(null)
    }


}