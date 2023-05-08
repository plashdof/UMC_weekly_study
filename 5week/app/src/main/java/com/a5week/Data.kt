package com.a5week

import android.util.Log

object Data {
    var memos : Array<MemoData> = arrayOf()
    val TAG = "debugging"

    // 메모 추가기능
    fun plusMemos(data : MemoData){
        memos = memos.plus(data)
    }

    // 메모 수정기능
    fun fixMemos(data : MemoData, position : Int){
        memos[position] = data
    }

    // 메모 삭제기능. deleteList 값이 true인 값으로 새로운 array 만든뒤, 덮어쓰기
    fun deleteMemos(deleteList : Array<Boolean>){
        var newMemos : Array<MemoData> = arrayOf()
        for(index in deleteList.indices){
            if(!deleteList[index]){
                newMemos = newMemos.plus(memos[index])
            }
        }
        memos = newMemos
    }

    // 삭제모드. 데이터의 mode 값을 1로 변경 (삭제체크표시 visible 처리)
    fun deleteMode(){
        for(i in memos){
            i.mode = 1
        }
    }

    // 일반모드. 데이터의 mode 값을 0로 변경 (삭제체크표시 invisible 처리)
    fun generalMode(){
        for(i in memos){
            i.mode = 0
        }
    }
}