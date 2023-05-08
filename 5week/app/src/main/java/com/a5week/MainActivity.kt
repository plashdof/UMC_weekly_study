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
    var deleteList : Array<Boolean> = arrayOf()

    private lateinit var binding : ActivityMainBinding

    // adapter 에서 Activity 접근할 수 있는 Room
    inner class RoomToAcitivity(){
        
        // 메모 길게 클릭시, 삭제모드 돌입
        fun deleteStart(){
            binding.cvDelete.visibility = View.VISIBLE
            Data.deleteMode()
            makeRecycler()
        }

        // adapter 의 삭제리스트 클릭할때마다, Activity의 삭제리스트도 update
        fun setDeleteList(getList : Array<Boolean>){
            deleteList = getList.copyOf()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Data.generalMode()
        makeRecycler()

        // 메모추가 버튼 이벤트 리스너
        binding.btnAdd.setOnClickListener {
            val intent = Intent(this, MakememoActivity::class.java)
            startActivity(intent)
        }

        // 메모삭제 버튼 이벤트 리스너
        binding.btnDelete.setOnClickListener{
            deleteItem()
            binding.cvDelete.visibility = View.INVISIBLE
            Data.generalMode()
            makeRecycler()
        }

    }

    private fun makeRecycler(){
        val adapter = MainAdapter(this, Data.memos, RoomToAcitivity())
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = GridLayoutManager(this,3)
    }


    @SuppressLint("NotifyDataSetChanged")
    fun addItem() {
        binding.recycler.adapter?.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun deleteItem(){
        Data.deleteMemos(deleteList)
        Log.d(TAG,"결과값 ${Data.memos.contentToString()}")
        binding.recycler.adapter?.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        addItem()
    }

}