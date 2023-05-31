package com.a8week.model

import android.util.Log
import com.a8week.db.MemoDatabase
import com.a8week.db.MemoEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

object Data {
    var memos : ArrayList<MemoData> = arrayListOf()
    val TAG = "debugging"
    var db: MemoDatabase? = null

    // 메모 추가기능
    fun plusMemos(data : MemoData){
        memos.add(data)
        val entityData = MemoEntity(0,data.text,data.title,data.date,data.time,data.mode)
        CoroutineScope(Dispatchers.IO).launch{
            db!!.memoDao().addMemo(entityData)
        }
    }

    // 메모 수정기능
    fun fixMemos(data : MemoData){
        val entityMemo = MemoEntity(data.id,data.text,data.title,data.date,data.time,data.mode)
        CoroutineScope(Dispatchers.IO).launch{
            db!!.memoDao().updateMemo(entityMemo)
        }
    }

    // 메모 삭제기능. deleteList 값이 true인 값으로 새로운 array 만든뒤, 덮어쓰기
    fun deleteMemos(deleteList : ArrayList<Int>){

        var memoIds = arrayListOf<Int>()
        for(i in deleteList){
            memoIds.add(memos[i].id)
        }
        var memoIdList = memoIds.toList()

        for(i in deleteList){
            memos.removeAt(i)
        }

        CoroutineScope(Dispatchers.IO).launch{
            db!!.memoDao().deleteMemo(memoIdList)
        }
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