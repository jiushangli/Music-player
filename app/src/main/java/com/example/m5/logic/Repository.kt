package com.example.m5.logic

import android.util.Log
import androidx.lifecycle.liveData
import com.example.m5.logic.model.MainNcResponse
import com.example.m5.logic.model.Song
import com.example.m5.logic.network.MusicNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

object Repository {

    fun searchMusic(keywords: String) = liveData(Dispatchers.IO) {
        val result = try {
            val musicResponse = MusicNetwork.searchMusic(keywords)
            if(musicResponse.code == "200"){
                Log.d("hucheng", musicResponse.toString())
                val songs = musicResponse.result.songs
                Result.success(songs)
            }else{
                Result.failure(RuntimeException(
                    "response code is : ${musicResponse.code}"
                ))
            }
        }catch (e: Exception){
            Log.d("hucheng", "Repository error")
            Log.d("hucheng", e.toString())
            Result.failure<List<Song>>(e)
        }
        emit(result)
    }


    fun getUri(id: Long, level: String) = liveData(Dispatchers.IO) {
        val result = try {
            val musicResponse = MusicNetwork.getUri(id, level)
            if(musicResponse.code == "200"){
                val song = musicResponse.data[0]
                Result.success(song)
            }else{
                Result.failure(RuntimeException(
                    "response code is ${musicResponse.code}"
                ))
            }

        }catch (e: Exception){
            Result.failure<Song>(e)
        }
        emit(result)
    }


    fun refreshNc() = liveData(Dispatchers.IO) {
        val result = try {
            coroutineScope {

                val deferrrdHotMusics = async {
                    MusicNetwork.getHotMusic()
                }

                val defrrdPlayList = async {
                    MusicNetwork.getHighQuality()
                }

                val defrrNewMusicAls = async {
                    MusicNetwork.getNewMusicAls()
                }


                val hotMusicResponse = deferrrdHotMusics.await()
                val playListResponse = defrrdPlayList.await()
                val newMusicAls = defrrNewMusicAls.await()

                if(playListResponse.code == "200" && hotMusicResponse.code == "200" && hotMusicResponse.message == "success" && newMusicAls.code == "200"){
                    val mainNcResponse = MainNcResponse(hotMusicResponse.data, playListResponse.playlists, newMusicAls.data)
                    Result.success(mainNcResponse)
                }else{
                    Result.failure(RuntimeException(
                        "error"
                    ))
                }


            }

        }catch (e: Exception){
            Result.failure(e)
        }
        emit(result)
    }














//    fun getHighQuality() = liveData(Dispatchers.IO) {
//        val result = try {
//            val highQualityResponse = MusicNetwork.getHighQuality()
//            if(highQualityResponse.code == "200"){
//                Log.d("hucheng", highQualityResponse.toString())
//
//                Result.success(highQualityResponse)
//            }else{
//                Result.failure(RuntimeException(
//                    "response code is ${highQualityResponse.code}"
//                ))
//            }
//        }catch (e: Exception){
//            Log.d("hucheng", "Repository error")
//            Log.d("hucheng", e.toString())
//            Result.failure<HighQualityResponse>(e)
//        }
//
//        emit(result)
//    }
//
//    fun getHotMusic() = liveData(Dispatchers.IO) {
//        val result = try {
//            val hotMusicResponse = MusicNetwork.getHotMusic()
//            if(hotMusicResponse.code == "200" && hotMusicResponse.message == "success"){
//                Result.success(hotMusicResponse.data)
//            }else{
//                Result.failure(RuntimeException(
//                    "response code is ${hotMusicResponse.code} and message is ${hotMusicResponse.message}"
//                ))
//            }
//
//        }catch (e: Exception){
//            Result.failure<List<HotMusic>>(e)
//        }
//
//        emit(result)
//    }


}