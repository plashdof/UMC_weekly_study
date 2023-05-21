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



        // BottomSheetDialog 에서 넘어오면 Thread 시작
        // 하단 버튼구성 바꾸기
        if(intent.hasExtra("startThread")){
            SingleTon.threadState = true
            startTimer()
            binding.btnSettime.visibility = View.GONE
            binding.layoutRunning.visibility = View.VISIBLE
        }else{
            binding.layoutRunning.visibility = View.GONE
            binding.btnSettime.visibility = View.VISIBLE
        }

        // 시간 셋팅 버튼 클릭 -> BottomSheetDialog 등장
        binding.btnSettime.setOnClickListener {
            val bottomSheet = BottomSheetDialog()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }

        
        // 리셋버튼 클릭 -> 값 초기화
        // threadState 로 Thread While문 탈출시켜 종료함
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

        // 일시정지 버튼 클릭 -> pauseState 로 Thread 무한루프에 가둠
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
        
        // 1초를 100으로 나눈값을 타이머에 표현하기 위해, 100 곱하기
        var timeForProgress = timeInt * 100
        
        Thread(){
            binding.progress.max = timeForProgress
            binding.progress.progress = timeForProgress
            
            while(timeForProgress >= 0 && SingleTon.threadState){
                runOnUiThread {
                    
                    // 1시간보다 위의 시간이면,  시간 : 분 : 초 표기

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
                        // 1시간보다 아래 시간이면,  분 : 초 : 미리초 표기

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


                // 일시정지 상태일때, 무한루프에 가둠
                while(SingleTon.pauseState){
                    Thread.sleep(1)
                }
                
                Thread.sleep(10)
                timeForProgress -= 1
                timeInt = timeForProgress / 100
                binding.progress.progress = timeForProgress

                // 설정한 시간 다되었을때,
                // 효과음 + text 변경
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