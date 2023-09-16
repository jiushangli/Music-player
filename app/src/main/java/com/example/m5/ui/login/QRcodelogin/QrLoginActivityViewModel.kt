package com.example.m5.ui.login.QRcodelogin

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.m5.logic.Repository
import java.util.Base64

class QrLoginActivityViewModel: ViewModel() {

    private val keyliveData = MutableLiveData<String>()
    private val codeliveData = MutableLiveData<Pair<String, String>>()
    private val codeStatusliveData = MutableLiveData<Pair<String, String>>()


//    private lateinit var key: String
//    private lateinit var qrCode: String

    val keyFocusData = Transformations.switchMap(keyliveData){timestamp->
        Repository.getKey(timestamp)
    }

    val codeFocusData = Transformations.switchMap(codeliveData){pair->
        Repository.getCode(pair.first, pair.second)
    }

    val codeStatusFocusData = Transformations.switchMap(codeStatusliveData){pair->
        Repository.getCodeStatue(pair.first, pair.second)
    }




    fun getKey(timestamp: String){
        keyliveData.value = timestamp
    }

    fun getCode(key: String, timestamp: String){
        codeliveData.value = Pair(key, timestamp)
    }

    fun getCodeStatus(key: String, timestamp: String){
        codeStatusliveData.value = Pair(key, timestamp)
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