package com.example.m5.ui.searchMusic

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.m5.MusicApplication
import com.example.m5.R
import com.example.m5.logic.model.Song

class MusicAdapter(val musicList: List<Song>): RecyclerView.Adapter<MusicAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val musicName: TextView = view.findViewById(R.id.musicName)
        val artName: TextView = view.findViewById(R.id.artName)
        val musicPicture: ImageView = view.findViewById(R.id.musicPicture)
//        val itemSearch: LinearLayout = view.findViewById(R.id.itemSearch)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
        val holder = ViewHolder(view)

        //点击事件
        holder.itemView.setOnClickListener{
            Log.d("hucheng", "点击")
            Toast.makeText(MusicApplication.context, "点击", Toast.LENGTH_SHORT).show()
            val position = holder.adapterPosition
            val music = musicList[position]
            val activity = SearchActivity.instance
            val id = music.id
            activity!!.viewModel.getUri(Pair(id, "sky"))
        }


        return holder
    }

    override fun getItemCount() = musicList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val music = musicList[position]
        holder.musicName.text = music.name
        holder.artName.text = music.ar.get(0).name



        Log.d("hucheng", "url: ${music.al.picUrl}")
        Glide.with(SearchActivity.instance!!)
            .load(music.al.picUrl)
            .into(holder.musicPicture)

    }
}