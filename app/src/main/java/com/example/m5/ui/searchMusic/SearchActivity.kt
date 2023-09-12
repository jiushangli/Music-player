package com.example.m5.ui.searchMusic

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.m5.R
import com.example.m5.databinding.ActivitySearchBinding
import com.example.m5.logic.model.Data
import com.example.m5.ui.player.PlayMusicViewModel


class SearchActivity : AppCompatActivity() {

    val viewModel by lazy { ViewModelProvider(this).get(SearchViewModel::class.java) }
    private lateinit var binding: ActivitySearchBinding

    private lateinit var adapterMusicList: MusicAdapter

    companion object{
        var instance: SearchActivity? = null
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.coolRed)
//        setContentView(R.layout.activity_search)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        instance = this


        //切换toolbar左边的背景
        binding.toolbarsearch.toolImage.setImageResource(R.drawable.back_icon)
        //输入框聚焦
        binding.toolbarsearch.searchMusicEdit.requestFocus()
        //键盘显示
        val imn = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imn.showSoftInput(binding.toolbarsearch.searchMusicEdit, InputMethodManager.SHOW_IMPLICIT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 在API 21及以上版本中设置输入模式为软键盘弹出
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        } else {
            // 在API 20及以下版本中设置输入模式为隐藏
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        }


        val layoutManagerMusciList = LinearLayoutManager(this)
        binding.showMusic.layoutManager = layoutManagerMusciList
        adapterMusicList = MusicAdapter(viewModel.musicList)
        binding.showMusic.adapter = adapterMusicList





        //点击隐藏事件

//        binding.show.setOnClickListener{
//            binding.toolbar.searchMusicEdit.clearFocus()
//            imn.hideSoftInputFromWindow(binding.root.windowToken, 0)
//        }


        //点击返回事件
        binding.toolbarsearch.toolImage.setOnClickListener {
            finish()
        }

        //点击搜索事件
        binding.toolbarsearch.searchMusic.setOnClickListener {
            Log.d("hucheng", "Search")
            val context = binding.toolbarsearch.searchMusicEdit.text.toString()
            Log.d("hucheng", "context: $context")
            if(context.isNotEmpty()){
                viewModel.searchMusic(context)


            }else{

            }
        }

        //监听
        viewModel.musicliveData.observe(this, Observer { result->
            val songs = result.getOrNull()
            if (songs != null){
                viewModel.musicList.clear()
                viewModel.musicList.addAll(songs)
                binding.showMusic.visibility = View.VISIBLE
                adapterMusicList.notifyDataSetChanged()
            }else{

            }
        })

        viewModel.uriliveData.observe(this, Observer { result->
            val song: Data? = (result as Result<Data>).getOrNull()
            Log.d("hucheng", "返回内容${song.toString()}")
            song?.let { noNullSong->
                Log.d("hucheng", "准备播放")
                if (PlayMusicViewModel.musicList == null){
                    PlayMusicViewModel.musicList = ArrayList()
                }

                //                添加音乐到列表
                PlayMusicViewModel.position++
                PlayMusicViewModel.musicList.add(PlayMusicViewModel.position, noNullSong)
                Log.d("hucheng", "musiclists: ${PlayMusicViewModel.musicList[PlayMusicViewModel.position].toString()}")
                //播放
//                if (Player.isStartService){
//                    Player.prepareMediaPlayer()
//                }else{
//                    Player.startService()
//                    Player.prepareMediaPlayer()
//                }
            }
        })




    }


}