package com.example.m5.ui.NetEaseCloudMusic

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.m5.logic.Repository
import com.example.m5.logic.model.HotMusic
import com.example.m5.logic.model.MusicAl
import com.example.m5.logic.model.PlayList

class MusicAcitivityViewModel: ViewModel() {

    private val refreshliveData = MutableLiveData<Any?>()



    var playLists = ArrayList<PlayList>()
    var hotMusicLists = ArrayList<HotMusic>()
    var newMusicesAls = ArrayList<MusicAl>()


    val mainLiveData = Transformations.switchMap(refreshliveData){
        Repository.refreshNc()
    }

    fun refresh(){
        refreshliveData.value = refreshliveData.value
    }












}