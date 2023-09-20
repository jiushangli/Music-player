package com.example.m5.logic.model

data class RecommendSongsResponse(val code: String, val data: RecommendData)

data class RecommendData(val dailySongs: List<DailySong>)

data class DailySong(val name:String, val id: Long, val ar: List<ArRecommend>, val al: AlRecommend, val reason: String)

data class ArRecommend(val id: Long, val name: String)

data class AlRecommend(val id: Long, val name: String, val picUrl: String)