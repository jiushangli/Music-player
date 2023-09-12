package com.example.m5

import android.app.Application
import android.content.Context

class MusicApplication: Application() {

    companion object{
        lateinit var context: Context
        const val TOKEN = ""
    }


    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}