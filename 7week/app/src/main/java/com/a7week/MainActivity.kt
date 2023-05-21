package com.a7week

import android.content.Intent
import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.a7week.databinding.ActivityMainBinding
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val TAG = "debugging"
    private val cal = Calendar.getInstance()
    private val soundPool = SoundPool.Builder().build()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        if(intent.hasExtra("startThread")){
            SingleTon.threadState = true
            startTimer()
            binding.btnSettime.visibility = View.GONE
            binding.layoutRunning.visibility = View.VISIBLE
        }else{
            binding.layoutRunning.visibility = View.GONE
            binding.btnSettime.visibility = View.VISIBLE
        }

        binding.btnSettime.setOnClickListener {
            val endSound = soundPool.load(this,R.raw.end,1)
            soundPool.play(endSound, 1F, 1F, 0,-1,1F)
            val bottomSheet = BottomSheetDialog()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }

        binding.btnReset.setOnClickListener {
            SingleTon.pauseState = false
            SingleTon.threadState = false
            binding.tvTime.text = "00:00:00"
            SingleTon.hours = 0
            SingleTon.minutes = 0
            SingleTon.seconds = 0
            binding.progress.max = 100
            binding.progress.progress = 100
            binding.layoutRunning.visibility = View.GONE
            binding.btnSettime.visibility = View.VISIBLE
        }

        binding.btnPause.setOnClickListener {
            if(SingleTon.pauseState){
                SingleTon.pauseState = false
                binding.btnPause.text = "일시정지"
            }else{
                SingleTon.pauseState = true
                binding.btnPause.text = "재개"
            }

        }

        binding.btnMusic.setOnClickListener {
            val intent = Intent(this,MusicActivity::class.java)
            startActivity(intent)
        }
    }

    private fun startTimer(){
        val endSound = soundPool.load(this@MainActivity,R.raw.end,1)
        var timeInt = 3600 * SingleTon.hours + 60 * SingleTon.minutes + SingleTon.seconds
        var timeForProgress = timeInt * 100
        Thread(){
            binding.progress.max = timeForProgress
            binding.progress.progress = timeForProgress

            while(timeForProgress >= 0 && SingleTon.threadState){
                runOnUiThread {
                    if(timeForProgress >= 360000){
                        val hour = timeInt / 3600
                        val minute = (timeInt % 3600) / 60
                        val second = timeInt % 60

                        var hourString = ""
                        var minuteString = ""
                        var secondString = ""

                        hourString = if(hour < 10) "0$hour:"
                        else "$hour:"
                        minuteString = if(minute < 10) "0$minute:"
                        else "$minute:"
                        secondString = if(second < 10) "0$second"
                        else "$second"

                        binding.tvTime.text = hourString + minuteString + secondString
                    }else{
                        val minute = (timeInt % 3600) / 60
                        val second = timeInt % 60
                        val milsecond = timeForProgress % 100

                        var minuteString = ""
                        var secondString = ""
                        var milsecondString = ""

                        minuteString = if(minute < 10) "0$minute:"
                        else "$minute:"
                        secondString = if(second < 10) "0$second:"
                        else "$second:"
                        milsecondString = if(milsecond < 10) "0$milsecond"
                        else "$milsecond"

                        binding.tvTime.text = minuteString + secondString + milsecondString
                    }
                }


                while(SingleTon.pauseState){
                    Thread.sleep(1)
                }
                Thread.sleep(10)
                timeForProgress -= 1
                timeInt = timeForProgress / 100
                binding.progress.progress = timeForProgress

                if(timeForProgress == -1){
                    Log.d(TAG,"end")
                    soundPool.play(endSound, 1F, 1F, 0,5,1F)
                    runOnUiThread {
                        binding.tvTime.text = "종료!!!"
                    }
                }
            }
        }.start()
    }

}