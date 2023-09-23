package com.example.m5.ui.recommend

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.m5.R
import com.example.m5.databinding.ActivityRecommendBinding
import com.example.m5.ui.AppConfig
import com.example.m5.ui.login.QRcodelogin.QrLoginActivityViewModel

class RecommendActivity : AppCompatActivity() {

    private val viewMode by lazy { ViewModelProvider(this)[RecommendActivityViewModel::class.java] }

    private lateinit var binding: ActivityRecommendBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.coolRed)
        setContentView(R.layout.activity_recommend)

        binding = ActivityRecommendBinding.inflate(layoutInflater)
        setContentView(binding.root)


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

        //获取每日推荐
        viewMode.getRecommend(AppConfig.cookie)

    }
}