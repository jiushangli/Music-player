package com.example.m5.ui.netEaseMineActivity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NetEaseMainViewModel : ViewModel() {
    var netEasePage: MutableLiveData<Int> = MutableLiveData(0)
}