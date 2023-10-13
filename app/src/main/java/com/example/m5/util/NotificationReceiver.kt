package com.example.m5.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.m5.MusicApplication
import com.example.m5.R
import com.example.m5.activity.PlayerActivity
import com.example.m5.activity.PlayerActivity.Companion.fIndex
import com.example.m5.data.musicListPA
import com.example.m5.data.songPosition
import com.example.m5.frag.NowPlaying
import com.example.m5.util.PlayMusic.Companion.isPlaying
import com.example.m5.util.PlayMusic.Companion.musicService

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(content: Context?, intent: Intent?) {
        when (intent?.action) {
            MusicApplication.PREVIOUS -> preNextSong(increment = false, context = content!!)
            MusicApplication.PLAY -> if (isPlaying) pauseMusic() else playMusic()

            MusicApplication.NEXT -> preNextSong(increment = true, context = content!!)

            MusicApplication.EXIT -> {
                exitApplication()
            }
        }
    }

    //根据通知栏里面的点击情况来控制音乐的播放和暂停,改变通知栏的图标以及播放界面的播放图标
    private fun playMusic() {
          isPlaying = true
          musicService!!.mediaPlayer!!.start()
          musicService!!.showNotification(R.drawable.pause_icon,1F)
        NowPlaying.binding.playPauseBtnNP.setImageResource(R.drawable.pause_icon)
    }

    private fun pauseMusic() {
          isPlaying = false
          musicService!!.mediaPlayer!!.pause()
          musicService!!.showNotification(R.drawable.play_icon,0F)
        NowPlaying.binding.playPauseBtnNP.setImageResource(R.drawable.play_icon)
    }

    private fun preNextSong(increment: Boolean, context: Context) {
        setSongPosition(increment = increment)
          musicService!!.createMediaPlayer(  musicListPA[  songPosition])

/*        //装载播放界面的专辑以及歌曲名
        Glide.with(context)
            .load(  musicListPA[  songPosition].imageUrl)
            .apply(RequestOptions().placeholder(R.drawable.moni1).centerCrop())
            .into(  binding.songImgPA)
          binding.songNamePA.text =
              musicListPA[  songPosition].name*/

        Glide.with(context)
            .load(  musicListPA[  songPosition].imageUrl)
            .apply(RequestOptions().placeholder(R.drawable.moni1).centerCrop())
            .into(NowPlaying.binding.songImgNP)
        NowPlaying.binding.songNameNP.text =
              musicListPA[  songPosition].name
        playMusic()
          fIndex =
            favouriteChecker(  musicListPA[  songPosition].id.toString())
        /*if (  isFavourite) {
              binding.favouriteBtnPA.setImageResource(R.drawable.favourite_icon)
        } else {
              binding.favouriteBtnPA.setImageResource(R.drawable.favourite_empty_icon)
        }*/
    }

}