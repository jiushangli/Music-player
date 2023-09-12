package com.example.m5.ui.searchMusic

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.m5.logic.Repository
import com.example.m5.logic.model.Song

class SearchViewModel: ViewModel() {

    private val getUriliveData = MutableLiveData<Pair<Long, String>>()
    private val searchliveData = MutableLiveData<String>()

    val musicList = ArrayList<Song>()

    val musicliveData = Transformations.switchMap(searchliveData){ query->
        Repository.searchMusic(query)
    }

    val uriliveData = Transformations.switchMap(getUriliveData){pair->
        Repository.getUri(pair.first, pair.second)
    }

    fun searchMusic(query: String){
        searchliveData.value = query
    }

    fun getUri(pair: Pair<Long, String>){
        getUriliveData.value = pair
    }



}