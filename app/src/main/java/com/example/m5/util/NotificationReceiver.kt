package com.example.m5.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.m5.MusicApplication
import com.example.m5.R
import com.example.m5.activity.PlayerActivity
import com.example.m5.frag.NowPlaying

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(content: Context?, intent: Intent?) {
        when (intent?.action) {
            MusicApplication.PREVIOUS -> preNextSong(increment = false, context = content!!)
            MusicApplication.PLAY -> if (PlayerActivity.isPlaying) pauseMusic() else playMusic()

            MusicApplication.NEXT -> preNextSong(increment = true, context = content!!)

            MusicApplication.EXIT -> {
                exitApplication()
            }
        }
    }

    //根据通知栏里面的点击情况来控制音乐的播放和暂停,改变通知栏的图标以及播放界面的播放图标
    private fun playMusic() {
        PlayerActivity.isPlaying = true
        PlayerActivity.musicService!!.mediaPlayer!!.start()
        PlayerActivity.musicService!!.showNotification(R.drawable.pause_icon,1F)
        PlayerActivity.binding.playPauseBtnPA.setImageResource(R.drawable.ic_pause)
        NowPlaying.binding.playPauseBtnNP.setImageResource(R.drawable.pause_icon)
    }

    private fun pauseMusic() {
        PlayerActivity.isPlaying = false
        PlayerActivity.musicService!!.mediaPlayer!!.pause()
        PlayerActivity.musicService!!.showNotification(R.drawable.play_icon,0F)
        PlayerActivity.binding.playPauseBtnPA.setImageResource(R.drawable.play_icon)
        NowPlaying.binding.playPauseBtnNP.setImageResource(R.drawable.play_icon)
    }

    private fun preNextSong(increment: Boolean, context: Context) {
        setSongPosition(increment = increment)
        PlayerActivity.musicService!!.createMediaPlayer()

        //装载播放界面的专辑以及歌曲名
        Glide.with(context)
            .load(PlayerActivity.musicListPA[PlayerActivity.songPosition].imageUrl)
            .apply(RequestOptions().placeholder(R.drawable.moni1).centerCrop())
            .into(PlayerActivity.binding.songImgPA)
        PlayerActivity.binding.songNamePA.text =
            PlayerActivity.musicListPA[PlayerActivity.songPosition].name

        Glide.with(context)
            .load(PlayerActivity.musicListPA[PlayerActivity.songPosition].imageUrl)
            .apply(RequestOptions().placeholder(R.drawable.moni1).centerCrop())
            .into(NowPlaying.binding.songImgNP)
        NowPlaying.binding.songNameNP.text =
            PlayerActivity.musicListPA[PlayerActivity.songPosition].name
        playMusic()
        PlayerActivity.fIndex =
            favouriteChecker(PlayerActivity.musicListPA[PlayerActivity.songPosition].id.toString())
        /*if (PlayerActivity.isFavourite) {
            PlayerActivity.binding.favouriteBtnPA.setImageResource(R.drawable.favourite_icon)
        } else {
            PlayerActivity.binding.favouriteBtnPA.setImageResource(R.drawable.favourite_empty_icon)
        }*/
    }

}