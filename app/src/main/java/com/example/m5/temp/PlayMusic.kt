package com.example.m5.temp

import android.media.MediaPlayer
import androidx.lifecycle.MutableLiveData
import com.example.m5.data.StandardSongData
import com.example.m5.logic.Repository
import com.example.m5.logic.model.Song
import com.example.m5.logic.network.MusicNetwork
import kotlinx.coroutines.NonCancellable.start

/**
 * 播放音乐
 */


 fun playMusicX(url: String) {
    val mediaPlayer = MediaPlayer()

//
//     val songUrl = Repository.getUri(url, "standard")



    // MediaPlayer 重置
    mediaPlayer.reset()
    // 初始化
    mediaPlayer.apply {
        // 设置音乐流类型
        setAudioStreamType(android.media.AudioManager.STREAM_MUSIC)
        // 设置音乐来源
        setDataSource(url)
        // 准备
        prepareAsync()
        // 准备完成监听
        setOnPreparedListener {
            // 开始播放
            start()
        }
        // 播放完成监听
        setOnCompletionListener {

        }

    }

}