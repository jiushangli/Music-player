package com.example.m5.util

import android.media.MediaPlayer
import android.util.Log
import com.example.m5.activity.PlayerActivity.Companion.nowPlayingId
import com.example.m5.data.SOURCE_LOCAL
import com.example.m5.data.StandardSongData
import com.example.m5.data.musicListPA
import com.example.m5.data.songPosition
import com.example.m5.logic.network.MusicNetwork
import com.example.m5.service.MusicService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.m5.util.setSongPosition


open class PlayMusic : MediaPlayer.OnCompletionListener {

    companion object {
        //?的含义是可以为空,!的含义是非空断言,!!的含义是非空断言,如果为空就抛出异常
        var musicService: MusicService? = null
        var isPlaying: Boolean = false
    }

    suspend fun createMediaPlayer(song: StandardSongData) {
        try {
            // 如果为空就创建一个,在什么时候为空呢,在第一次进入的时候为空
            if (musicService!!.mediaPlayer == null) musicService!!.mediaPlayer =
                MediaPlayer()

            musicService!!.mediaPlayer!!.reset()

            var songUrl: String

            if (song.source == SOURCE_LOCAL) {
                songUrl = song.localInfo?.path!!
            } else {
                run {
                    withContext(Dispatchers.IO) {
                        // 在 IO 线程中执行异步任务，比如网络请求
                        songUrl =
                            MusicNetwork.getUrl(song.id.toString(), "Standard").data[0].url
                    }
                }
            }
            // 回到主线程

            Log.d("yqhy", "返回内容${songUrl}")
            Log.d("yqhy", "音乐的id : ${song.id}")

             musicService!!.mediaPlayer!!.setDataSource(songUrl)
             musicService!!.mediaPlayer!!.prepare()
             musicService!!.mediaPlayer!!.start()
            // ... 其他 UI 更新操作 ...
             isPlaying = true
            //替换播放按钮

             musicService!!.mediaPlayer!!.setOnCompletionListener(this)
             nowPlayingId =
                 musicListPA[ songPosition].id!!

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCompletion(p0: MediaPlayer?) {
        GlobalScope.launch(Dispatchers.Main) {
            setSongPosition(increment = true)
            createMediaPlayer( musicListPA[ songPosition])
        }

    }
}
