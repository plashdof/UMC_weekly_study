package com.a9week.util

object LoginInfo {
    private var social = ""

    fun getSocial():String{
        return social
    }

    fun setSocial(temp : String){
        social = temp
    }
}