package com.example.m5.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.m5.util.Music
import com.example.m5.activity.PlayerActivity
import com.example.m5.R
import com.example.m5.databinding.MusicViewBinding

class FavouriteAdapter(private val context: Context, private var musicList: ArrayList<Music>) :
    RecyclerView.Adapter<FavouriteAdapter.MyHolder>() {

    class MyHolder(binding: MusicViewBinding) : RecyclerView.ViewHolder(binding.root) {
        val title = binding.songNameMV
        val artist = binding.artistMV
        val image = binding.imageMV
        val root = binding.root
        val moreAciton = binding.moreAction
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(MusicViewBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.title.text = musicList[position].title
        holder.artist.text = musicList[position].artist
        Glide.with(context)
            .load(musicList[position].artUri)
            .apply(RequestOptions().placeholder(R.drawable.moni2).centerCrop())
            .into(holder.image)

        holder.root.setOnClickListener {
            val intent = Intent(context, PlayerActivity::class.java).setAction("your.custom.action")
            intent.putExtra("index", position)
            intent.putExtra("class", "FavouriteAdapter")
            ContextCompat.startActivity(context, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return musicList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateFavourites(newList: ArrayList<Music>) {
        musicList = ArrayList()
        musicList.addAll(newList)
        notifyDataSetChanged()
    }
}