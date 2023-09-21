package com.example.m5.frag

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.m5.R
import com.example.m5.activity.MainActivity
import com.example.m5.activity.PlayerActivity
import com.example.m5.databinding.FragmentNowPlayingBinding
import com.example.m5.util.setSongPosition

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
        binding = FragmentNowPlayingBinding.bind(view)
        binding.root.visibility = View.INVISIBLE
        binding.playPauseBtnNP.setOnClickListener {
            if (PlayerActivity.musicService != null) {
                if (PlayerActivity.isPlaying) pauseMusic()
                else playMusic()
            }
        }
        binding.nextBtnNP.setOnClickListener {
            if (PlayerActivity.musicService != null) {
                setSongPosition(increment = true)
                PlayerActivity.musicService!!.createMediaPlayer()

                Glide.with(this)
                    .load(PlayerActivity.musicListPA[PlayerActivity.songPosition].imageUrl)
                    .apply(RequestOptions().placeholder(R.drawable.yqhy).centerCrop())
                    .into(binding.songImgNP)
                binding.songNameNP.text =
                    PlayerActivity.musicListPA[PlayerActivity.songPosition].name
                binding.artistNP.text =
                    PlayerActivity.musicListPA[PlayerActivity.songPosition].artists?.get(0)?.name
                PlayerActivity.musicService!!.showNotification(R.drawable.pause_icon,1F)
                playMusic()
            }
        }

        binding.preBtnNP.setOnClickListener {
            if (PlayerActivity.musicService != null) {
                setSongPosition(increment = false)
                PlayerActivity.musicService!!.createMediaPlayer()

                Glide.with(this)
                    .load(PlayerActivity.musicListPA[PlayerActivity.songPosition].imageUrl)
                    .apply(RequestOptions().placeholder(R.drawable.yqhy).centerCrop())
                    .into(binding.songImgNP)
                binding.songNameNP.text =
                    PlayerActivity.musicListPA[PlayerActivity.songPosition].name
                binding.artistNP.text =
                    PlayerActivity.musicListPA[PlayerActivity.songPosition].artists?.get(0)?.name
                PlayerActivity.musicService!!.showNotification(R.drawable.pause_icon,1F)
                playMusic()
            }
        }


        binding.root.setOnClickListener {
            if (PlayerActivity.musicService != null) {
                val intent =
                    Intent(
                        requireContext(),
                        PlayerActivity::class.java
                    ).setAction("your.custom.action")
                intent.putExtra("index", PlayerActivity.songPosition)
                intent.putExtra("class", "NowPlaying")
                ContextCompat.startActivity(requireContext(), intent, null)
            }
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        binding.root.visibility = View.VISIBLE

        if (PlayerActivity.musicService != null) {
            binding.songNameNP.isSelected = true
            Glide.with(this)
                .load(PlayerActivity.musicListPA[PlayerActivity.songPosition].imageUrl)
                .apply(RequestOptions().placeholder(R.drawable.yqhy).centerCrop())
                .into(binding.songImgNP)
            binding.songNameNP.text = PlayerActivity.musicListPA[PlayerActivity.songPosition].name
            binding.artistNP.text = PlayerActivity.musicListPA[PlayerActivity.songPosition].artists?.get(0)?.name
            if (PlayerActivity.isPlaying) binding.playPauseBtnNP.setImageResource(R.drawable.pause_icon)
            else binding.playPauseBtnNP.setImageResource(R.drawable.play_frag)
        }
    }

    private fun playMusic() {
        PlayerActivity.musicService!!.mediaPlayer!!.start()
        binding.playPauseBtnNP.setImageResource(R.drawable.pause_icon)
        PlayerActivity.musicService!!.showNotification(R.drawable.pause_icon,1F)
//        PlayerActivity.binding.nextBtnPA.setIconResource(R.drawable.pause_icon)
        PlayerActivity.isPlaying = true
    }

    private fun pauseMusic() {
        PlayerActivity.musicService!!.mediaPlayer!!.pause()
        binding.playPauseBtnNP.setImageResource(R.drawable.play_frag)
        PlayerActivity.musicService!!.showNotification(R.drawable.play_frag,0F)
//        PlayerActivity.binding.nextBtnPA.setIconResource(R.drawable.play_icon)
        PlayerActivity.isPlaying = false
    }
}
