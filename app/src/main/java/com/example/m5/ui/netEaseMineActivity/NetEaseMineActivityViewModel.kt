package com.example.m5.ui.netEaseMineActivity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.m5.logic.Repository

class NetEaseMineActivityViewModel: ViewModel() {


    var isLongined = false
    var uid: Long? = null


    private val statusliveData = MutableLiveData<String>()

    val statusFocusData = Transformations.switchMap(statusliveData){ tiemstamp->
        Repository.getStatus(tiemstamp)
    }

    fun getStatus(timestamp: String){
        statusliveData.value = timestamp
    }

}