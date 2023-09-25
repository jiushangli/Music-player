package com.example.m5.ui.searchMusic

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.m5.R
import com.example.m5.activity.PlayerActivity
import com.example.m5.databinding.ActivitySearchBinding
import com.example.m5.logic.model.Data

class SearchActivity : AppCompatActivity() {

    val viewModel by lazy { ViewModelProvider(this)[SearchViewModel::class.java] }
    lateinit var binding: ActivitySearchBinding

    private lateinit var adapterMusicList: SearchMusicAdapter

    companion object{
        var instance: SearchActivity? = null
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.coolRed)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        instance = this

        //切换toolbar左边的背景
        binding.toolbarSearch.toolImage.setImageResource(R.drawable.back_icon)
        //输入框聚焦
        binding.toolbarSearch.searchMusicEdit.requestFocus()
        //键盘显示
        val imn = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imn.showSoftInput(binding.toolbarSearch.searchMusicEdit, InputMethodManager.SHOW_IMPLICIT)

        // 在API 21及以上版本中设置输入模式为软键盘弹出
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)

        val layoutManagerMusicList = LinearLayoutManager(this)
        binding.showMusic.layoutManager = layoutManagerMusicList

        adapterMusicList = SearchMusicAdapter(this,viewModel.musicList)

        binding.showMusic.adapter = adapterMusicList


        //点击返回事件
        binding.toolbarSearch.toolImage.setOnClickListener {
            finish()
        }

        //点击搜索事件
        binding.toolbarSearch.searchMusic.setOnClickListener {
            Log.d("hucheng", "Search")
            val context = binding.toolbarSearch.searchMusicEdit.text.toString()
            Log.d("hucheng", "context: $context")
            if(context.isNotEmpty()){
                viewModel.searchMusic(context)
            }
        }

        //监听
        viewModel.musicLiveData.observe(this){ result->
            val songs = result.getOrNull()
            songs?.reversed()
            if (songs != null){
                viewModel.musicList.clear()
                viewModel.musicList.addAll(songs)
                binding.showMusic.visibility = View.VISIBLE
                adapterMusicList.notifyDataSetChanged()
            }
        }

        viewModel.urlLiveData.observe(this) { result->
            val song: Data? = (result as Result<Data>).getOrNull()
            Log.d("hucheng", "返回内容${song.toString()}")
            song?.let { noNullSong->

                //把音乐转成标准格式,传入参数url实现
                viewModel.musicList[viewModel.postion].let {
                    PlayerActivity.musicListNE.add(it.switchToStandard(noNullSong.url))
                }

                val intent = Intent(this, PlayerActivity::class.java).setAction("your.custom.action")
                intent.putExtra("index", 0)
                intent.putExtra("class", "SearchActivity")
                ContextCompat.startActivity(this, intent, null)

            }
        }

    }
}