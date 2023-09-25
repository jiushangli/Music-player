package com.example.m5.ui.searchMusic

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.m5.logic.Repository
import com.example.m5.logic.model.Song

class SearchViewModel: ViewModel() {

    private val getUrlLiveData = MutableLiveData<Pair<String, String>>()
    private val searchLiveData = MutableLiveData<String>()

    val musicList = ArrayList<Song>()
    var postion: Int = 0

    val musicLiveData = Transformations.switchMap(searchLiveData){ query->
        Repository.searchMusic(query)
    }

    val urlLiveData = Transformations.switchMap(getUrlLiveData){pair->
        Repository.getUrl(pair.first, pair.second)
    }



    fun searchMusic(query: String){
        searchLiveData.value = query
    }

    fun getUrl(pair: Pair<String, String>){
        getUrlLiveData.value = pair
    }



}