package com.example.m5.ui.searchMusic

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.m5.R
import com.example.m5.activity.PlayerActivity
import com.example.m5.activity.PlaylistActivity
import com.example.m5.activity.PlaylistDetails
import com.example.m5.data.StandardSongData
import com.example.m5.databinding.MusicViewBinding
import com.example.m5.logic.model.Song


class SearchMusicAdapter(
    private val context: Context, private var musicList: ArrayList<Song>,
) : RecyclerView.Adapter<SearchMusicAdapter.MyHolder>() {

    class MyHolder(binding: MusicViewBinding) : RecyclerView.ViewHolder(binding.root) {
        val title = binding.songNameMV
        val artist = binding.artistMV
        val image = binding.imageMV
        val root = binding.root
        val moreAction = binding.moreAction

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(MusicViewBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.title.text = musicList[position].name
        holder.artist.text = musicList[position].ar[0].name
//        holder.duration.text = formatDuration(musicList[position].duration)
        //加载图片
        Glide.with(context)
            .load(musicList[position].al?.picUrl)
            .apply(RequestOptions().placeholder(R.drawable.moni2).centerCrop())
            .into(holder.image)
        holder.moreAction.setOnClickListener {
//            showItemSelectDialog(context, position)
        }
        holder.root.setOnClickListener {
            SearchActivity.instance!!.viewModel.postion = position
            SearchActivity.instance!!.viewModel.getUri(Pair(musicList[position].id, "standard"))






        }
    }

    override fun getItemCount(): Int {
        return musicList.size
    }

    @SuppressLint("NotifyDataSetChanged")
/*    fun updateMusicList(searchList: ArrayList<Music>) {
        musicList = ArrayList()
        musicList.addAll(searchList)
        notifyDataSetChanged()
    }*/

    private fun sendIntent(ref: String, pos: Int) {
        val intent = Intent(context, PlayerActivity::class.java).setAction("your.custom.action")
        intent.putExtra("index", pos)
        intent.putExtra("class", ref)
        ContextCompat.startActivity(context, intent, null)
    }




}
