package com.example.m5.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {

    private const val  BASE_URL = "http://192.168.104.220:3000"

    private val retorfit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass: Class<T>): T = retorfit.create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)
}