package com.example.m5.ui.recommend

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.net.wifi.p2p.WifiP2pManager.GroupInfoListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.m5.R
import com.example.m5.databinding.ActivityRecommendBinding
import com.example.m5.databinding.MusicViewBinding
import com.example.m5.logic.model.DailySong

class RecommendAdapter(val context: Context, private val dailySongs: List<DailySong>): RecyclerView.Adapter<RecommendAdapter.Holder>() {

    class Holder(binding: MusicViewBinding): RecyclerView.ViewHolder(binding.root){
        val title = binding.songNameMV
        val artist = binding.artistMV
        val image = binding.imageMV
        val root = binding.root
        val moreAction = binding.moreAction
        val songInf = binding.songInf

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = MusicViewBinding.inflate(LayoutInflater.from(context), parent, false)
        val holder = Holder(view)

        holder.itemView.setOnClickListener {
            //设置点击事件
            Toast.makeText(context, "here", Toast.LENGTH_SHORT).show()
        }

        return holder

    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.title.text = dailySongs[position].name
        holder.artist.text = dailySongs[position].ar[0].name+"-"+dailySongs[position].al.name
        holder.artist.textSize = 10F

        dailySongs[position].reason.let {
            holder.songInf.text = it
            holder.songInf.visibility= View.VISIBLE
    //            holder.songInf.textColor = R.color.bordeaux_red
            holder.songInf.setTextColor(R.color.bordeaux_red)
            holder.songInf.setTypeface(null, Typeface.BOLD)
            holder.songInf.textSize = 10F
        }


        //加载图片
        Glide.with(context)
            .load(dailySongs[position].al.picUrl)
            .apply(RequestOptions().placeholder(R.drawable.moni2).centerCrop())
            .into(holder.image)
    }

    override fun getItemCount() = dailySongs.size
}