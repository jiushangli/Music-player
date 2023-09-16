package com.example.m5.ui.login.QRcodelogin

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.m5.logic.Repository
import com.example.m5.logic.model.LoginCodeStatusResponse
import java.util.Base64
import java.util.TimeZone
import kotlin.concurrent.thread

class QrLoginActivityViewModel: ViewModel() {

    private val keyliveData = MutableLiveData<String>()
    private val codeliveData = MutableLiveData<Pair<String, String>>()
    private val codeStatusliveData = MutableLiveData<String>()



    lateinit var key: String
//    private lateinit var qrCode: String

    companion object{
        var cookie: String? = null
    }


    val keyFocusData = Transformations.switchMap(keyliveData){timestamp->
        Repository.getKey(timestamp)
    }

    val codeFocusData = Transformations.switchMap(codeliveData){pair->
        Repository.getCode(pair.first, pair.second)
    }

    val codeStatusFocusData = Transformations.switchMap(codeStatusliveData){key->
        Repository.getCodeStatue(key)
    }



    fun getKey(timestamp: String){
        keyliveData.value = timestamp
    }

    fun getCode(key: String, timestamp: String){
        codeliveData.value = Pair(key, timestamp)
    }

    fun getCodeStatus(key: String){
        codeStatusliveData.value = key
    }




    //图像解码
    @RequiresApi(Build.VERSION_CODES.O)
    fun base642Bitmap(base64: String): Bitmap{
        val parts = base64.split(",")
        val decode = Base64.getDecoder().decode(parts[1])
        val mBitmap = BitmapFactory.decodeByteArray(decode, 0, decode.size)

        return mBitmap
    }


}