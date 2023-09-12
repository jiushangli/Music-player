package com.example.m5.ui.NetEaseCloudMusic

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.m5.R
import com.example.m5.databinding.ActivityNcBinding
import com.example.m5.ui.searchMusic.SearchActivity


class NcActivity : AppCompatActivity() {

    val viewModel by lazy { ViewModelProvider(this).get(MusicAcitivityViewModel::class.java) }

    private lateinit var binding: ActivityNcBinding

//    private lateinit var adapterSearch: MusicAdapter
    private lateinit var adapterPlayList: PlayListsAdapter
    private lateinit var adapterHotMusic: HotMiusicAdapter
    private lateinit var adapterNewMusicAls: ViewPagerAdapter


    companion object {
        var instance: NcActivity? =null
    }


    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.coolRed)
        setContentView(R.layout.activity_nc)


        instance = this

        binding = ActivityNcBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.toolbarnc.searchMusic.visibility = View.GONE
        setSupportActionBar(binding.toolbarnc.toolbar)

//        val layoutManagerSearch = LinearLayoutManager(this)
//        binding.recyclerView.layoutManager = layoutManagerSearch
//        adapterSearch = MusicAdapter(viewModel.musicList)
//        binding.recyclerView.adapter = adapterSearch

        val layoutManagerPlayLists = LinearLayoutManager(this)
        layoutManagerPlayLists.orientation = LinearLayoutManager.HORIZONTAL
        binding.recyclerViewPlaylist.layoutManager = layoutManagerPlayLists
        adapterPlayList = PlayListsAdapter(viewModel.playLists)
        binding.recyclerViewPlaylist.adapter = adapterPlayList

        val layoutManagerHotMiusic = LinearLayoutManager(this)
        layoutManagerHotMiusic.orientation = LinearLayoutManager.HORIZONTAL
        binding.hotMusics.layoutManager = layoutManagerHotMiusic
        adapterHotMusic = HotMiusicAdapter(viewModel.hotMusicLists)
        binding.hotMusics.adapter = adapterHotMusic


        adapterNewMusicAls = ViewPagerAdapter(viewModel.newMusicesAls)
        binding.loopViewPager.apply {
            adapter = adapterNewMusicAls
        }


        viewModel.refresh()


        //搜索框聚焦事件：跳转搜索页面
        binding.toolbarnc.searchMusicEdit.setOnFocusChangeListener{_, hasFocus->
            if(hasFocus){
                //跳转
                val intent = Intent(this, SearchActivity::class.java)
                startActivity(intent)
            }else{

            }
        }


        //获取歌单
//        viewModel.getHighQuality()
//        viewModel.getHotMusices()


//        binding.searchMusic.setOnClickListener {
//            val context = binding.searchMusicEdit.text.toString()
//            if(context.isNotEmpty()){
//                viewModel.searchMusic(context)
//            }else{
//
//            }
//        }



        //添加搜索事件

        //绑定
//        viewModel.musicliveData.observe(this, Observer { result->
//            val musices = result.getOrNull()
//            if(musices != null){
//                binding.recyclerView.visibility = View.VISIBLE
//                viewModel.musicList.clear()
//                viewModel.musicList.addAll(musices)
//                adapterSearch.notifyDataSetChanged()
//
//            }else{
//
//            }
//        })
//
//        viewModel.highQualityliveData.observe(this, Observer { result->
//            val highQualityResponse = result.getOrNull()
//            if(highQualityResponse != null){
//                viewModel.playLists.addAll(highQualityResponse.playlists)
//                adapterPlayList.notifyDataSetChanged()
//            }else{
//
//            }
//        })

        viewModel.mainLiveData.observe(this, Observer { result->
            val mainNcResponse = result.getOrNull()
            if(mainNcResponse != null){
                viewModel.playLists.addAll(mainNcResponse.playLists)
                viewModel.hotMusicLists.addAll(mainNcResponse.hotMusics)
                viewModel.newMusicesAls.addAll(mainNcResponse.newMusicesAls)
                Log.d("hucheng", "Activity palylists ${viewModel.playLists}")
                Log.d("hucheng", "Activity hotmusics ${viewModel.hotMusicLists}")
                Log.d("hucheng", "Activity newmusicAl ${viewModel.newMusicesAls}")
                adapterPlayList.notifyDataSetChanged()
                adapterPlayList.notifyDataSetChanged()
                adapterNewMusicAls.notifyDataSetChanged()
            }else{

            }
        })


    }


    override fun onRestart() {
        super.onRestart()
        binding.toolbarnc.searchMusicEdit.clearFocus()
    }

    override fun onDestroy() {
        super.onDestroy()
        instance = null
    }
}