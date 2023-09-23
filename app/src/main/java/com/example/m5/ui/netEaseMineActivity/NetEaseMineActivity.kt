package com.example.m5.ui.netEaseMineActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.m5.activity.MainActivity
import com.example.m5.databinding.ActivityNetEaseMineBinding
import com.example.m5.ui.AppConfig
import com.example.m5.ui.login.QRcodelogin.QrLoginActivity
import com.example.m5.util.setStatusBarTextColor
import com.example.m5.util.transparentStatusBar

class NetEaseMineActivity : AppCompatActivity() {

    lateinit var binding: ActivityNetEaseMineBinding

    private val viewModel by lazy { ViewModelProvider(this).get(NetEaseMineActivityViewModel::class.java)}

    companion object{
        var install: NetEaseMineActivity? = null
    }

    private var isOncreated: Boolean = false

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
        //第一次进入这个页面：onCreate，不管isLogined是什么，都需要进行一次判断，或者说，这次判断就是用来决定isLogined的
        viewModel.statusFocusData.observe(this, Observer { result->
            val result = result.getOrNull()
            if(result?.data?.profile == null){
                //未登录
                AppConfig.isLogined = false
                //其实本来就是false了，这里再写一次只是为了更好明白逻辑
            }else if(result?.data?.profile != null){
                //登录

                //这里的登录只记录信息
                AppConfig.isLogined = true
                result?.data?.profile.let { it->
                    AppConfig.let { app->
                        app.uid = it.userId
                        app.nickname = it.nickname
                        app.avatarUrl = it.avatarUrl
                    }
                }
                //加载昵称图片
                binding.usernameNMA.text = AppConfig.nickname
                NetEaseMineActivityViewModel.loadPicture(AppConfig.avatarUrl!!)
            }
        })



        //首次进来获取保存的cookie进行登陆状态查询(cookie没保存返回null)
        if (viewModel.isCookieSaved()){
            AppConfig.cookie = viewModel.getSavedCookie()!!
            viewModel.getStatus(System.currentTimeMillis().toString(), viewModel.getSavedCookie()!!)
        }
        else{
            AppConfig.cookie = ""
            viewModel.getStatus(System.currentTimeMillis().toString(), "")
        }

        isOncreated = true

    }


    override fun onResume() {
        super.onResume()

        //如果经历了onCreate，就不查询，没经历就查询
        if (!isOncreated){
            viewModel.getStatus(System.currentTimeMillis().toString(), viewModel.getSavedCookie()!!)
        }

        isOncreated = false

        if (AppConfig.isLogined){

        }else{

        }


    }


}