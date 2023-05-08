package com.a5week

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.a5week.databinding.ActivityMakememoBinding
import java.text.SimpleDateFormat

class MakememoActivity : AppCompatActivity(){

    private lateinit var binding : ActivityMakememoBinding
    val TAG = "debugging"
    var text = ""
    var title = ""
    private var position = 0
    private var date = ""
    private var time = ""
    private var mode = 0
    private var fixState = false

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMakememoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 수정하는 경우 데이터 덮어쓰기
        if(intent.hasExtra("position")){
            fixState = true
            position = intent.getStringExtra("position")!!.toInt()
            text = intent.getStringExtra("text").toString()
            title = intent.getStringExtra("title").toString()
            binding.etText.setText(text)
            if(title=="제목없음"){
                title=""
            }
            binding.etTitle.setText(title)

        }

        binding.btnAdd.setOnClickListener {
            
            // 메모작성 시점 날짜 시간 저장
            val currentTime = System.currentTimeMillis()
            val timeFormat = SimpleDateFormat("HH:mm")
            val dateFormat = SimpleDateFormat("MM월 dd일")
            date = dateFormat.format(currentTime)
            time = timeFormat.format(currentTime)


            if(title.isBlank()) title = "제목없음"
            
            val data = MemoData(text,title,date,time, mode)
            
            // 수정하는 경우 구분
            if(fixState){
                Data.fixMemos(data,position)
            }else{
                Data.plusMemos(data)
            }

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


        binding.etText.addTextChangedListener(object : TextWatcher{
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                text = binding.etText.text.toString()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
        })

        binding.etTitle.addTextChangedListener(object : TextWatcher{
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                title = binding.etTitle.text.toString()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
        })
    }
}