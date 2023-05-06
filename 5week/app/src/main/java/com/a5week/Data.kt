package com.a5week

import android.util.Log

object Data {
    var memos : Array<MemoData> = arrayOf()
    val TAG = "debugging"

    fun remove(index: Int){
        if (index >= 0 && index < memos.size) {
            val result = memos.toMutableList()
            result.removeAt(index)
            memos = result.toTypedArray()
        }
    }

    fun plusMemos(data : MemoData){
        memos = memos.plus(data)
    }

    fun fixMemos(data : MemoData, position : Int){
        memos[position] = data
    }

    fun deleteMemos(positions : Array<Int>){
        Log.d(TAG,)
        for(i in positions){
            remove(i)
        }
    }
}