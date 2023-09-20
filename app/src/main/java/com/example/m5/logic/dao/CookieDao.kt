package com.example.m5.logic.dao

import android.content.Context
import android.util.Log
import com.example.m5.MusicApplication

object CookieDao {

    private fun sharePreferences() = MusicApplication.context.getSharedPreferences("Cookie", Context.MODE_PRIVATE)

    fun saveCookie(cookie: String){
        val edit = sharePreferences().edit()
        edit.putString("cookie", cookie)
        edit.apply()



    }

    fun getSavedCookie(): String?{
        val cookie = sharePreferences().getString("cookie", "")
        return cookie
    }

    fun isCookieSaved(): Boolean{
        val pd = sharePreferences().contains("cookie")
        return pd
    }



}