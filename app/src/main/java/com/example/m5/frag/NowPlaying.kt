package com.example.m5.frag

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.m5.R
import com.example.m5.activity.MainActivity
import com.example.m5.activity.PlayerActivity
import com.example.m5.data.musicListPA
import com.example.m5.data.songPosition
import com.example.m5.databinding.FragmentNowPlayingBinding
import com.example.m5.util.PlayMusic
import com.example.m5.util.PlayMusic.Companion.isPlaying
import com.example.m5.util.PlayMusic.Companion.musicService
import com.example.m5.util.setSongPosition
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class NowPlaying : Fragment() {
    companion object {
        @Suppress("StaticFieldLeak")
        lateinit var binding: FragmentNowPlayingBinding
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireContext().theme.applyStyle(MainActivity.currentTheme[MainActivity.themeIndex], true)
        val view = inflater.inflate(R.layout.fragment_now_playing, container, false)

        /*        if (musicService != null) {
                    Glide.with(this)
                        .load(musicListPA[songPosition].imageUrl)
                        .apply(RequestOptions().placeholder(R.drawable.yqhy).centerCrop())
                        .into(binding.songImgNP)
                    binding.songNameNP.text = "Kiss"
        //            binding.songNameNP.text = musicListPA[songPosition].name
                    binding.artistNP.text = musicListPA[songPosition].artists?.get(0)?.name
                    if (isPlaying) binding.playPauseBtnNP.setImageResource(R.drawable.pause_icon)
                    else binding.playPauseBtnNP.setImageResource(R.drawable.play_frag)
                }*/

        binding = FragmentNowPlayingBinding.bind(view)

        binding.playPauseBtnNP.setOnClickListener {
            if (musicService != null) {
                if (isPlaying) pauseMusic()
                else playMusic()
            }
        }
        binding.nextBtnNP.setOnClickListener {

            GlobalScope.launch(Dispatchers.Main) {
                if (musicService != null) {
                    setSongPosition(increment = true)
                    PlayMusic().createMediaPlayer(musicListPA[songPosition])
                    Glide.with(this@NowPlaying)
                        .load(musicListPA[songPosition].imageUrl)
                        .apply(RequestOptions().placeholder(R.drawable.yqhy).centerCrop())
                        .into(binding.songImgNP)
                    binding.songNameNP.text =
                        musicListPA[songPosition].name
                    binding.artistNP.text =
                        musicListPA[songPosition].artists?.get(0)?.name
                    playMusic()
                }
            }
        }

        binding.preBtnNP.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {

                if (musicService != null) {
                    setSongPosition(increment = false)
                    musicService!!.createMediaPlayer(musicListPA[songPosition])

                    Glide.with(this@NowPlaying)
                        .load(musicListPA[songPosition].imageUrl)
                        .apply(RequestOptions().placeholder(R.drawable.yqhy).centerCrop())
                        .into(binding.songImgNP)
                    binding.songNameNP.text =
                        musicListPA[songPosition].name
                    binding.artistNP.text =
                        musicListPA[songPosition].artists?.get(0)?.name

                    playMusic()
                }
            }
        }


        binding.root.setOnClickListener {
            if (musicService != null) {
                val intent =
                    Intent(
                        requireContext(),
                        PlayerActivity::class.java
                    ).setAction("your.custom.action")
                intent.putExtra("index", songPosition)
                intent.putExtra("class", "NowPlaying")
                ContextCompat.startActivity(requireContext(), intent, null)
            }
        }
        return view
    }

    override fun onResume() {
        super.onResume()

//        Toast.makeText(context, "kiss", Toast.LENGTH_SHORT).show()

        if (musicService != null) {
            Glide.with(this)
                .load(musicListPA[songPosition].imageUrl)
                .apply(RequestOptions().placeholder(R.drawable.yqhy).centerCrop())
                .into(binding.songImgNP)
            binding.songNameNP.text = musicListPA[songPosition].name
            binding.artistNP.text = musicListPA[songPosition].artists?.get(0)?.name
            if (isPlaying) binding.playPauseBtnNP.setImageResource(R.drawable.pause_icon)
            else binding.playPauseBtnNP.setImageResource(R.drawable.play_frag)
        }
    }


    private fun playMusic() {
//        musicService!!.mediaPlayer!!.start()
        binding.playPauseBtnNP.setImageResource(R.drawable.pause_icon)

        isPlaying = true
    }

    private fun pauseMusic() {
        musicService!!.mediaPlayer!!.pause()
        binding.playPauseBtnNP.setImageResource(R.drawable.play_frag)
//        musicService!!.showNotification(R.drawable.play_frag, 0F)
        isPlaying = false
    }
}

fun main() = runBlocking {
    println("Start")

    launch {
        delay(1000L)  // 模拟一个耗时操作
        println("Hello from coroutine!")
    }

    println("End")
}