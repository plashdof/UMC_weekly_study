package com.week2.instahome.models

data class PostData(
    val profileImg : Int,
    val profileName : String,
    val likeCount : String,
    val firstText : String,
    val secondText : String,
    val hashTag : String,
    val commentCount : String,
    val date : String,

    val imgList : Array<Int>
)
