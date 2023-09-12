package com.example.m5.ui.NetEaseCloudMusic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.m5.R
import com.example.m5.logic.model.PlayList

class PlayListsAdapter(val playLists: List<PlayList>): RecyclerView.Adapter<PlayListsAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val playList: ImageView = view.findViewById(R.id.playList)
        val descripte: TextView = view.findViewById(R.id.descripte)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_playlist, parent, false)
        val holder = ViewHolder(view)

        //点击事件

        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //加载
        val playList = playLists[position]
        holder.descripte.text = playList.name
        Glide.with(NcActivity.instance!!)
            .load(playList.coverImgUrl)
            .into(holder.playList)
    }

    override fun getItemCount() = playLists.size
}