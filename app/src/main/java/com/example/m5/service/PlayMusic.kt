package com.example.m5.service

import android.content.Context
import com.example.m5.data.StandardSongData

/**
 * 播放音乐
 */
fun playMusic(context: Context?, song: StandardSongData, songList: ArrayList<StandardSongData>, playAll: Boolean = false) {
    // 获取 position
    val position = if (songList.indexOf(song) == -1) {
        0
    } else {
        songList.indexOf(song)
    }

}