package com.week2.instahome

import android.app.Application
import android.content.Context

class App : Application() {

    //  앱의 context 를 instance 변수에 저장
    init{
        instance=this
    }


    companion object{
        private var instance : App? = null

        // 앱의 context 를 불러오는 함수
        fun context() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
    }
}