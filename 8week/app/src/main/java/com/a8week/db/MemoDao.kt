package com.a8week.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.a8week.model.MemoData

@Dao
interface MemoDao {

    @Query("SELECT * FROM memo_table")
    fun getAllMemo() : List<MemoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMemo(memoEntity : MemoEntity)

    @Query("DELETE FROM memo_table WHERE id IN (:memoIds)")
    fun deleteMemo(memoIds: List<Int>)

    @Update
    fun updateMemo(memoEntity: MemoEntity)


    @Query("DELETE FROM memo_table")
    fun clearAll()
}