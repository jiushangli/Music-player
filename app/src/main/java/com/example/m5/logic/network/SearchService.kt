package com.example.m5.logic.network

import com.example.m5.logic.model.MusicSearchResponse
import com.example.m5.logic.model.SongResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface SearchService {

    @GET("/cloudsearch?limit=6")
    fun searchMusic(@Query("keywords") keywords: String): Call<MusicSearchResponse>

    @GET("/song/url/v1")
    fun getUri(@Query("id")id: Long, @Query("level")level: String): Call<SongResponse>


}