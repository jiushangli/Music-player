package com.example.m5.ui.recommend

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.m5.logic.Repository
import com.example.m5.logic.model.DailySong

class RecommendActivityViewModel: ViewModel() {

    private var recommendliveData = MutableLiveData<String>()

    var dailySongs =  ArrayList<DailySong>()


    val recommendFocus = Transformations.switchMap(recommendliveData){coookie->
        Repository.getRecommend(coookie)
    }

    fun getRecommend(cookie: String?){
        recommendliveData.value = cookie
    }

}