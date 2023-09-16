package com.example.m5.ui.netEaseMineActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import androidx.lifecycle.ViewModelProvider
import com.example.m5.activity.MainActivity
import com.example.m5.databinding.ActivityNetEaseMineBinding
import com.example.m5.ui.login.QRcodelogin.QrLoginActivity
import com.example.m5.util.setStatusBarTextColor
import com.example.m5.util.transparentStatusBar

class NetEaseMineActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNetEaseMineBinding

    private val viewModel by lazy { ViewModelProvider(this).get(NetEaseMineActivityViewModel::class.java)}

    companion object{
        var install: NetEaseMineActivity? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val themeEditor = getSharedPreferences("THEMES", MODE_PRIVATE)
        MainActivity.themeIndex = themeEditor.getInt("themeIndex", 0)
        setTheme(MainActivity.currentTheme[MainActivity.themeIndex])
        binding = ActivityNetEaseMineBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //透明状态栏以及文字颜色设定
        transparentStatusBar(window)
        setStatusBarTextColor(window, false)

        install = this


        binding.userPerson.setOnClickListener {
            val intent = Intent(this, QrLoginActivity::class.java)
            startActivity(intent)
        }





        //绑定
        viewModel.statusFocusData.observe(this, Observer { result->
            val result = result.getOrNull()
            if(result?.data?.profile == null){
                viewModel.isLongined = false
                viewModel.uid = null
                Log.d("hucheng", "未登录")
            }else if(result?.data?.profile != null){
                viewModel.isLongined = true
                viewModel.uid = result?.data?.profile.userId
            }
        })

        //登录与否的差异



    }


    override fun onResume() {
        super.onResume()
        viewModel.getStatus(System.currentTimeMillis().toString())
    }


}