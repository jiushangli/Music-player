package com.example.m5.logic

import android.util.Log
import androidx.lifecycle.liveData
import com.example.m5.logic.dao.CookieDao
import com.example.m5.logic.model.CodeData
import com.example.m5.logic.model.KeyData
import com.example.m5.logic.model.LoginCodeStatusResponse
import com.example.m5.logic.model.LoginStatusResponse
import com.example.m5.logic.model.MainNcResponse
import com.example.m5.logic.model.RecommendSongsResponse
import com.example.m5.logic.model.Song
import com.example.m5.logic.network.MusicNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

object Repository {

    fun searchMusic(keywords: String) = liveData(Dispatchers.IO) {
        val result = try {
            val musicResponse = MusicNetwork.searchMusic(keywords)
            if (musicResponse.code == "200") {
                Log.d("hucheng", musicResponse.toString())
                val songs = musicResponse.result.songs
                Result.success(songs)
            } else {
                Result.failure(
                    RuntimeException(
                        "response code is : ${musicResponse.code}"
                    )
                )
            }
        } catch (e: Exception) {
            Log.d("hucheng", "Repository error")
            Log.d("hucheng", e.toString())
            Result.failure<List<Song>>(e)
        }
        emit(result)
    }


    fun getUri(id: Long, level: String) = liveData(Dispatchers.IO) {
        val result = try {
            val musicResponse = MusicNetwork.getUri(id, level)
            if (musicResponse.code == "200") {
                val song = musicResponse.data[0]
                Result.success(song)
            } else {
                Result.failure(
                    RuntimeException(
                        "response code is ${musicResponse.code}"
                    )
                )
            }
        } catch (e: Exception) {
            Result.failure<Song>(e)
        }
        emit(result)
    }


    fun refreshNc() = liveData(Dispatchers.IO) {
        val result = try {
            coroutineScope {

                val deferredHotMusics = async {
                    MusicNetwork.getHotMusic()
                }

                val deferredPlayList = async {
                    MusicNetwork.getHighQuality()
                }

                val deferredNewMusicAls = async {
                    MusicNetwork.getNewMusicAls()
                }


                val hotMusicResponse = deferredHotMusics.await()
                val playListResponse = deferredPlayList.await()
                val newMusicAls = deferredNewMusicAls.await()

                if (playListResponse.code == "200" && hotMusicResponse.code == "200" && hotMusicResponse.message == "success" && newMusicAls.code == "200") {
                    val mainNcResponse = MainNcResponse(
                        hotMusicResponse.data,
                        playListResponse.playlists,
                        newMusicAls.data
                    )
                    Result.success(mainNcResponse)
                } else {
                    Result.failure(
                        RuntimeException(
                            "error"
                        )
                    )
                }


            }

        } catch (e: Exception) {
            Result.failure(e)
        }
        emit(result)
    }


    fun getKey(timestamp: String) = liveData(Dispatchers.IO) {
        val result = try {
            val key = MusicNetwork.getKey(timestamp)
            if (key.code == "200"){
                Result.success(key.data)
            }else{
                Result.failure(
                    RuntimeException(
                        "error"
                    )
                )
            }

        }catch (e: Exception){
            Result.failure<KeyData>(e)
        }

        emit(result)
    }

    fun getCode(key: String, timestamp: String) = liveData(Dispatchers.IO){

        val result = try {
            val Qrcode = MusicNetwork.getCode(key, timestamp)
            if(Qrcode.code == "200"){
                Result.success(Qrcode.data)
            }else{
                Result.failure(
                    RuntimeException(
                        "error"
                    )
                )
            }

        }catch (e: Exception){
            Result.failure<CodeData>(e)
        }

        emit(result)
    }

    //查询二维码扫码情况
    fun getCodeStatue(key: String) = liveData(Dispatchers.IO) {

        while (true){
            val result = try {

                Log.d("hucheng", "key : $key")
                val qrStatus = MusicNetwork.getCodeStatue(key, System.currentTimeMillis().toString())

                if (qrStatus?.code == "803"){
                    Result.success(qrStatus)
                }else{
                    Thread.sleep(1000)
                    continue
                }

            }catch (e: Exception){
                Result.failure<LoginCodeStatusResponse>(e)
            }

            emit(result)
            break
        }



    }

    //获取登录状态
    fun getStatus(timestamp: String, cookie: String) = liveData(Dispatchers.IO){
        val result = try {

            val status = MusicNetwork.getStatus(timestamp, cookie)

            Log.d("hucheng", "timestamp: $timestamp  status: $status")

            if (status != null){
                Result.success(status)
            }else{
                Result.failure(
                    RuntimeException(
                        "error"
                    )
                )
            }

        }catch (e: Exception){
            Result.failure<LoginStatusResponse>(e)
        }

        emit(result)

    }





    //获取每日推荐
    fun getRecommend(cookie: String) = liveData(Dispatchers.IO) {
        val result = try {
            val recommend = MusicNetwork.getRecommend(cookie)
            if (recommend.code == "200"){
                Result.success(recommend)
            }else{
                Result.failure(
                    RuntimeException(
                        "error"
                    )
                )
            }
        }catch (e: Exception){
            Result.failure<RecommendSongsResponse>(e)
        }
        emit(result)
    }





    //持久化层
    fun saveCookie(cookie: String) = CookieDao.saveCookie(cookie)

    fun getSavedCookie() = CookieDao.getSavedCookie()

    fun isCookieSaved() = CookieDao.isCookieSaved()



}