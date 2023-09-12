package com.example.m5

import android.app.Application
import android.content.Context

class MusicApplication: Application() {

    // 你好
    companion object{
        lateinit var context: Context
        const val TOKEN = ""
    }


    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}