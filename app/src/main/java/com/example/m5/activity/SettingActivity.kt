package com.example.m5.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.m5.R
import com.example.m5.databinding.ActivitySettingBinding
import com.example.m5.util.setStatusBarTextColor
import com.example.m5.util.transparentStatusBar


class SettingActivity : AppCompatActivity() {
    lateinit var binding: ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.coolBlack)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        transparentStatusBar(window)
        setStatusBarTextColor(window, true)
//        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);  //透明状态栏
        supportActionBar?.title = "设置"

        /*        when (MainActivity.themeIndex) {
                    0 -> binding.coolCyanTheme.setBackgroundColor(Color.WHITE)
                    1 -> binding.coolRedTheme.setBackgroundColor(Color.WHITE)
                    2 -> binding.coolGreenTheme.setBackgroundColor(Color.WHITE)
                    3 -> binding.coolBlueTheme.setBackgroundColor(Color.WHITE)
                    4 -> binding.coolBlackTheme.setBackgroundColor(Color.WHITE)
                }*/

        binding.coolGreenTheme.setOnClickListener { saveTheme(0) }
        binding.coolRedTheme.setOnClickListener { saveTheme(1) }
        binding.coolCyanTheme.setOnClickListener { saveTheme(2) }
        binding.coolBlueTheme.setOnClickListener { saveTheme(3) }
        binding.coolBlackTheme.setOnClickListener { saveTheme(4) }

        binding.navAbout.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    AboutActivity::class.java
                ).setAction("your.custom.action")
            )
        }
        binding.navFeedback.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    FeedbackActivity::class.java
                ).setAction("your.custom.action")
            )
        }

//        binding.imgVague.refreshBG(binding.root);

//        BlurImageView.doBlur(binding.imgVague, binding.root, 20, 1)
//        binding.versionName.text = setVersionDetails()
        //用于排序到时候用得上
        /*        binding.sortBtn.setOnClickListener {
                    val menuList = arrayOf("最近添加", "按名称排序", "按时长排序")
                    var currentSort = MainActivity.sortOrder

                    val builder = MaterialAlertDialogBuilder(this)
                    builder.setTitle("Sorting")
                        .setPositiveButton("OK") { _, _ ->
                            val editor = getSharedPreferences("SORTING", MODE_PRIVATE).edit()
                            editor.putInt("sortOrder", currentSort)
                            editor.apply()
                        }
                        .setSingleChoiceItems(menuList, currentSort) { _, which ->
                            currentSort = which
                        }
                    val customDialog = builder.create()
                    customDialog.show()
                }*/
    }

    private fun saveTheme(index: Int) {
        if (MainActivity.themeIndex != index) {
            val editor = getSharedPreferences("THEMES", MODE_PRIVATE).edit()
            editor.putInt("themeIndex", index)
            editor.apply()
            startActivity(
                Intent(
                    this,
                    MainActivity::class.java
                ).setAction("your.custom.action")
            )
        }else{
            startActivity(
                Intent(
                    this,
                    MainActivity::class.java
                ).setAction("your.custom.action")
            )
        }
    }

    private fun setVersionDetails(): String {
        return "Version Name: 1.0"
    }
    override fun onStart() {
        super.onStart()
        binding.blurLayout.startBlur()
        binding.blurLayout.lockView()
    }

    override fun onStop() {
        super.onStop()
        binding.blurLayout.pauseBlur()
    }
}