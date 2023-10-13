package com.example.m5.ui.recommend

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.m5.data.StandardSongData
import com.example.m5.logic.Repository
import com.example.m5.logic.model.DailySong
import com.example.m5.logic.model.Song

class RecommendActivityViewModel: ViewModel() {

    private var recommendLiveData = MutableLiveData<String>()
    private val getUrlLiveData = MutableLiveData<Pair<String, String>>()

    var dailySongs =  ArrayList<DailySong>()
    val musicList = ArrayList<Song>()
    var position: Int = 0


    val recommendFocus = Transformations.switchMap(recommendLiveData){ cookie->
        Repository.getRecommend(cookie)
    }


    val urlLiveData = Transformations.switchMap(getUrlLiveData){pair->
        Repository.getUrl(pair.first, pair.second)
    }

    fun getRecommend(cookie: String?){
        recommendLiveData.value = cookie
    }

    fun getUrl(pair: Pair<String, String>){
        getUrlLiveData.value = pair
    }


}