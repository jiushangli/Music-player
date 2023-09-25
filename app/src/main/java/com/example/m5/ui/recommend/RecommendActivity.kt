package com.example.m5.ui.recommend

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.m5.R
import com.example.m5.activity.PlayerActivity
import com.example.m5.databinding.ActivityRecommendBinding
import com.example.m5.logic.model.Data
import com.example.m5.ui.AppConfig
import com.example.m5.ui.login.QRcodelogin.QrLoginActivityViewModel

class RecommendActivity : AppCompatActivity() {

    val viewMode by lazy { ViewModelProvider(this)[RecommendActivityViewModel::class.java] }

    lateinit var binding: ActivityRecommendBinding
    companion object{
        var instance: RecommendActivity? = null
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
        val adapterRecommend = RecommendAdapter(this, viewMode.dailySongs)
        binding.recommendRecyclerView.adapter = adapterRecommend



        //数据绑定
        viewMode.recommendFocus.observe(this) { result->
            val recommend = result.getOrNull()
            Log.d("hucheng", recommend.toString())
            if (recommend != null){
                viewMode.dailySongs.clear()
                viewMode.dailySongs.addAll(recommend.data.dailySongs)
                adapterRecommend.notifyDataSetChanged()
            }
        }

        viewMode.urlLiveData.observe(this) { result->
            Log.d("hucheng", "$result")
            val song: Data? = (result as Result<Data>).getOrNull()
            Log.d("hucheng", "返回内容${song.toString()}")
            song?.let { noNullSong->

                //把音乐转成标准格式,传入参数url实现
                viewMode.dailySongs[viewMode.position].let {
                    Log.d("hucheng", "这个音乐: $it")
                    PlayerActivity.musicListNE.add(it.switchToStandard("null"))
                }

                val intent = Intent(this, PlayerActivity::class.java).setAction("your.custom.action")
                intent.putExtra("index", 0)
                intent.putExtra("class", "SearchActivity")
                ContextCompat.startActivity(this, intent, null)
            }
        }

        //获取每日推荐
        viewMode.getRecommend(AppConfig.cookie)

    }
}