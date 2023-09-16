package com.example.m5.ui.netEaseMineActivity

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.example.m5.logic.Repository
import com.example.m5.ui.login.QRcodelogin.QrLoginActivityViewModel
import kotlin.concurrent.thread

class NetEaseMineActivityViewModel: ViewModel() {


    companion object{
        var isLongined = false
        var uid: Long? = null
        var nickname: String? = null
        var avatarUrl: String? = null

        //网络图片加载图片
        fun loadPicture(url: String){
                Glide.with(NetEaseMineActivity.install!!)
                    .load(url)
                    .into(NetEaseMineActivity.install!!.binding.userImgNMA)
        }

    }

    private val statusliveData = MutableLiveData<Pair<String, String>>()

    val statusFocusData = Transformations.switchMap(statusliveData){ pair->
        Repository.getStatus(pair.first, pair.second)
    }


    fun getStatus(timestamp: String, cookie: String){
        Log.d("hucheng", "查询登录状态")
        Log.d("hucheng", "cookie:  ${QrLoginActivityViewModel.cookie}")
        statusliveData.value = Pair(timestamp, cookie)
    }







}