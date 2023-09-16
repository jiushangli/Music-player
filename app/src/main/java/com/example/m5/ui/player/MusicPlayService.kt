package com.example.m5.ui.player

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class MusicPlayService : Service() {


    private val myBinder =  MyBinder()

    inner class MyBinder: Binder(){
        fun currentService(): MusicPlayService{
            return this@MusicPlayService
        }
    }

    override fun onBind(intent: Intent): IBinder {
        return myBinder
    }
}

//听我说