package com.example.m5.ui.login.QRcodelogin

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.m5.R
import com.example.m5.databinding.ActivityQrLoginBinding

class QrLoginActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProvider(this).get(QrLoginActivityViewModel::class.java) }

    private lateinit var binding: ActivityQrLoginBinding

    companion object{
        var install: QrLoginActivity? = null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.coolRed)
        setContentView(R.layout.activity_qr_login)

        binding = ActivityQrLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        install = this

        //绑定
        viewModel.keyFocusData.observe(this, Observer { result->
            val result = result.getOrNull()
            if (result != null){
                if(result?.code == "200"){
                    val key = result.unikey
                    Log.d("hucheng", key)
                    viewModel.getCode(key, System.currentTimeMillis().toString())
                }
            }

        })

        viewModel.codeFocusData.observe(this, Observer{result->
            val result = result.getOrNull()
            if(result != null){
                Log.d("hucheng", "${result.qrurl} + ${result.qrimg}")
                if(result.qrimg != null){
                    binding.qrCodeImage.setImageBitmap(viewModel.base642Bitmap(result.qrimg))
                }
            }
        })


        //开始时候获取
        viewModel.getKey(System.currentTimeMillis().toString())


    }
}