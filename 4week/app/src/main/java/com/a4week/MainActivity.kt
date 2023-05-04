package com.a4week

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.a4week.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val TAG = "debugging"
    private var textStore = ""
    private var text = ""
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(TAG, "MainAct : onCreate")

        binding.btnNext.setOnClickListener {

            val intent = Intent(this, SecondActivity::class.java)
                .putExtra("text",text)
            startActivity(intent)
        }

        binding.etMain.addTextChangedListener(object : TextWatcher{
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                text = binding.etMain.text.toString()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {}
        })


    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "MainAct : onRestart")
        val intent = Intent(this, DialogActivity::class.java)
        startActivity(intent)
    }

    override fun onStart(){
        super.onStart()
        Log.d(TAG, "MainAct : onStart")
    }

    override fun onResume(){
        super.onResume()
        Log.d(TAG, "MainAct : onResume")
        if(Singleton.answer == "no"){
            textStore=""
        }

        binding.etMain.setText(textStore)


    }

    override fun onPause(){
        super.onPause()
        Log.d(TAG, "MainAct : onPause")
        textStore = binding.etMain.text.toString()

    }

    override fun onStop(){
        super.onStop()
        Log.d(TAG, "MainAct : onStop")
    }

    override fun onDestroy(){
        super.onDestroy()
        Log.d(TAG, "MainAct : onDestroy")
    }
}