package com.example.m5.ui.login.QRcodelogin

import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.m5.R
import com.example.m5.databinding.ActivityQrLoginBinding
import com.example.m5.ui.AppConfig
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class QrLoginActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProvider(this)[QrLoginActivityViewModel::class.java] }

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

        Log.d("hucheng", "onCreate")

        //绑定
        //获取key
        viewModel.keyFocusData.observe(this, Observer { result->
            val result = result.getOrNull()
            if (result != null){
                if(result?.code == "200"){
                    val key = result.unikey
                    Log.d("hucheng", key)
                    viewModel.key = result?.unikey!!
                    Log.d("hucheng", "key : ${viewModel.key}")
                    viewModel.getCode(key, System.currentTimeMillis().toString())
                }
            }

        })

        //获取二维码
        viewModel.codeFocusData.observe(this, Observer{result->
            val result = result.getOrNull()
            if(result != null){
                Log.d("hucheng", "${result.qrurl} + ${result.qrimg}")
                if(result.qrimg != null){
                    binding.qrCodeImage.setImageBitmap(viewModel.base642Bitmap(result.qrimg))
                    viewModel.bitmap = viewModel.base642Bitmap(result.qrimg)
                    viewModel.getCodeStatus(viewModel.key)

                }
            }
        })

        //监听扫码状态
        viewModel.codeStatusFocusData.observe(this, Observer { result->
//            val cookie = result
//            AppConfig.cookie = result?.cookie!!
//            Log.d("hucheng", "cookie : ${AppConfig.cookie}")
//            AppConfig.isLogined = true
//            Log.d("hucheng", "cookie: ${AppConfig.cookie}")
//
//            //保存cookie
//            viewModel.saveCookie(AppConfig.cookie!!)
//            finish()
            val cookieNow = result.getOrNull()
            Log.d("hucheng", "返回的cookie：$cookieNow")
            AppConfig.isChanged = false
            Log.d("hucheng", "Appconfig cookie: ${AppConfig.cookie}")
            viewModel.saveCookie(AppConfig.cookie)
            finish()

        })





        //开始时候获取
        viewModel.getKey(System.currentTimeMillis().toString())


        //点击保存图片
        binding.saveQrCode.setOnClickListener {
            Toast.makeText(this, "点击了", Toast.LENGTH_SHORT).show()
            if (viewModel.bitmap == null)
                Toast.makeText(this, "图片尚未加载", Toast.LENGTH_SHORT).show()
            else{
                val picName = "NeteaseLoginQRCode.jpg"
                try {
                    val temp = ByteArrayOutputStream()
                    viewModel.bitmap.compress(Bitmap.CompressFormat.JPEG, 0, temp)
                    val byin = ByteArrayInputStream(temp.toByteArray())
                    viewModel.insert2Album(byin, picName)
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }


    }

    override fun onRestart() {
        super.onRestart()
        Log.d("hucheng", "onRestart")
        Log.d("hucheng", "${AppConfig.cookie}")

//        if (AppConfig.cookie != null)
//            finish()
    }

    override fun onStop() {
        super.onStop()
        Log.d("hucheng", "onStop")
    }

    override fun onStart() {
        super.onStart()
        Log.d("hucheng", "onStart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("hucheng", "onDestroy")
    }

    override fun onResume() {
        super.onResume()
        Log.d("hucheng", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("hucheng", "onPause")
    }


}
