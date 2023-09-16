package com.example.m5.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.m5.R
import com.example.m5.databinding.ActivityMainBinding
import com.example.m5.databinding.ActivityNetEaseMineBinding
import com.example.m5.util.setStatusBarTextColor
import com.example.m5.util.transparentStatusBar

class NetEaseMineActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNetEaseMineBinding
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

    }
}