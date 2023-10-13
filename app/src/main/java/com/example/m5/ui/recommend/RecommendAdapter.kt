package com.example.m5.ui.recommend

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.media.AudioManager
import android.os.IBinder
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.m5.R
import com.example.m5.data.StandardSongData
import com.example.m5.data.musicListPA
import com.example.m5.data.songPosition
import com.example.m5.databinding.MusicViewBinding
import com.example.m5.frag.NowPlaying
import com.example.m5.logic.model.DailySong
import com.example.m5.service.MusicService
import com.example.m5.util.PlayMusic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RecommendAdapter(val context: Context, val dailySongs: List<DailySong>) :
    RecyclerView.Adapter<RecommendAdapter.Holder>(), ServiceConnection {

    class Holder(binding: MusicViewBinding) : RecyclerView.ViewHolder(binding.root) {
        val title = binding.songNameMV
        val artist = binding.artistMV
        val image = binding.imageMV
        val root = binding.root
        val moreAction = binding.moreAction
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = MusicViewBinding.inflate(LayoutInflater.from(context), parent, false)
        return Holder(view)

    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.title.text = dailySongs[position].name
        holder.artist.text = dailySongs[position].ar[0].name

        /*dailySongs[position].reason.let {
            holder.songInf.text = it
            holder.songInf.visibility = View.VISIBLE
            //            holder.songInf.textColor = R.color.bordeaux_red
            holder.songInf.setTextColor(R.color.bordeaux_red)
            holder.songInf.setTypeface(null, Typeface.BOLD)
            holder.songInf.textSize = 10F
        }*/

        //加载图片
        Glide.with(context)
            .load(dailySongs[position].al.picUrl)
            .apply(RequestOptions().placeholder(R.drawable.moni2).centerCrop())
            .into(holder.image)

        holder.moreAction.setOnClickListener {
            Toast.makeText(context, "暂无更多操作", Toast.LENGTH_SHORT).show()
        }


        holder.root.setOnClickListener {
//            RecommendActivity.instance!!.viewMode.position = position
//            RecommendActivity.instance!!.viewMode.getUrl(Pair(dailySongs[position].id, "standard"))

            musicListPA = dailySongToSong()
            songPosition = position

            if (PlayMusic.musicService == null)
                startService(musicListPA, shuffle = false, false, position)
            else {
                GlobalScope.launch(Dispatchers.Main) {
                    PlayMusic().createMediaPlayer(musicListPA[position])
                }
            }
            PlayMusic.isPlaying = true
            NowPlaying.binding
            Glide.with(context)
                .load(musicListPA[songPosition].imageUrl)
                .apply(RequestOptions().placeholder(R.drawable.yqhy).centerCrop())
                .into(NowPlaying.binding.songImgNP)

            NowPlaying.binding.songNameNP.text = musicListPA[songPosition].name
            NowPlaying.binding.artistNP.text = musicListPA[songPosition].artists?.get(0)?.name

            if (PlayMusic.isPlaying) NowPlaying.binding.playPauseBtnNP.setImageResource(R.drawable.pause_icon)
            else NowPlaying.binding.playPauseBtnNP.setImageResource(R.drawable.play_frag)
        }
    }

    override fun getItemCount() = dailySongs.size


    //url不应该保存在歌曲里
    private fun dailySongToSong(): ArrayList<StandardSongData> {
        val list = ArrayList<StandardSongData>()
        for (song in dailySongs) {
            list.add(song.switchToStandard())
        }
        return list
    }

    fun startService(
        playlist: ArrayList<StandardSongData>,
        shuffle: Boolean,
        playNext: Boolean = false,
        position: Int
    ) {
        val intent = Intent(context, MusicService::class.java)
        context.bindService(intent, this, AppCompatActivity.BIND_AUTO_CREATE)
        context.startService(intent)
        musicListPA = ArrayList()
        musicListPA.addAll(playlist)
        songPosition = position
        if (shuffle) musicListPA.shuffle()
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        GlobalScope.launch(Dispatchers.Main) {
            if (PlayMusic.musicService == null) {
                val binder = service as MusicService.MyBinder
                PlayMusic.musicService = binder.currentService()
                PlayMusic.musicService!!.audioManager =
                    context.getSystemService(AppCompatActivity.AUDIO_SERVICE) as AudioManager
                PlayMusic.musicService!!.audioManager.requestAudioFocus(
                    PlayMusic.musicService,
                    AudioManager.STREAM_MUSIC,
                    AudioManager.AUDIOFOCUS_GAIN
                )
            }
            PlayMusic().createMediaPlayer(musicListPA[songPosition])
//            musicService!!.seekBarSetup()
        }
    }

    override fun onServiceDisconnected(p0: ComponentName?) {
        PlayMusic.musicService = null
    }
}