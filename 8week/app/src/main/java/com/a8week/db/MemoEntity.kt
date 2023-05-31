package com.a8week.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "memo_table")
class MemoEntity (
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val text : String,
    val title : String,
    val date : String,
    val time : String,
    val mode : Int
)