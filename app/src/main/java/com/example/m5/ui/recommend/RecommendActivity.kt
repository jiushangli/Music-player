package com.example.m5.ui.recommend

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.m5.R
import com.example.m5.adapter.MusicAdapterX
import com.example.m5.data.StandardSongData
import com.example.m5.databinding.ActivityRecommendBinding
import com.example.m5.logic.model.DailySong
import com.example.m5.ui.AppConfig


class RecommendActivity : AppCompatActivity() {

    val viewMode by lazy { ViewModelProvider(this)[RecommendActivityViewModel::class.java] }

    lateinit var binding: ActivityRecommendBinding
    companion object{
        var instance: RecommendActivity? = null

        var recommendMusic:List<StandardSongData> = ArrayList()
    }
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.coolRed)
        setContentView(R.layout.activity_recommend)

        binding = ActivityRecommendBinding.inflate(layoutInflater)
        setContentView(binding.root)

        instance = this

        //绑定adapter
        val layoutManagerRecommend = LinearLayoutManager(this)
        binding.recommendRecyclerView.layoutManager = layoutManagerRecommend
        val musicAdapter = MusicAdapterX(this,viewMode.dailySongs )
        binding.recommendRecyclerView.adapter = musicAdapter


        //数据绑定
        viewMode.recommendFocus.observe(this) { result->
            val recommend = result.getOrNull()
            Log.d("hucheng", recommend.toString())
            if (recommend != null){
                viewMode.dailySongs.clear()
                viewMode.dailySongs.addAll(dailySongToSong(recommend.data.dailySongs))
                recommendMusic = viewMode.dailySongs
                musicAdapter.notifyDataSetChanged()
            }
        }

        //获取每日推荐
        viewMode.getRecommend(AppConfig.cookie)

    }

    private fun dailySongToSong(songs: List<DailySong>): ArrayList<StandardSongData> {
        val list = ArrayList<StandardSongData>()
        for (song in songs) {
            list.add(song.switchToStandard())
        }
        return list
    }
}