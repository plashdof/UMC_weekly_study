package com.a5week

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.a5week.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val TAG = "debugging"
    var deleteList : Array<Int> = arrayOf(0,1)

    private lateinit var binding : ActivityMainBinding

    inner class RoomToAcitivity(){
        fun deleteStart(){
            binding.cvDelete.visibility = View.VISIBLE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = MainAdapter(this, Data.memos, RoomToAcitivity())
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = GridLayoutManager(this,3)

        binding.btnAdd.setOnClickListener {
            val intent = Intent(this, MakememoActivity::class.java)
            startActivity(intent)
        }

        binding.btnDelete.setOnClickListener{
            deleteItem()
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItem() {
        binding.recycler.adapter?.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun deleteItem(){
        Log.d(TAG, "delete Item Start")
        Data.deleteMemos(deleteList)
        binding.recycler.adapter?.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        addItem()
    }

}