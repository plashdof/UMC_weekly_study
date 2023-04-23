package com.week2.a3week

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.week2.a3week.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var getResultText : ActivityResultLauncher<Intent>
    private var text = ""
    private val RC_OK = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getResultText = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){ result ->

            Log.d("aaaaa","성공")
            if(result.resultCode == RC_OK){
                Log.d("aaaaa","성공")
                val backtext = result.data?.getStringExtra("back")
                Toast.makeText(this,backtext,Toast.LENGTH_SHORT).show()
            }
        }



        binding.etMain.addTextChangedListener(object : TextWatcher{
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                text = binding.etMain.text.toString()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {}
        })

        binding.btnMain.setOnClickListener {
            if(text.isBlank()){
                Toast.makeText(this,"텍스트를 입력해주세요",Toast.LENGTH_SHORT).show()
            }else{
                val intent = Intent(this,SecondActivity::class.java)
                intent.putExtra("text",text)
                getResultText.launch(intent)
            }
        }

    }
}