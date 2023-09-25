package com.example.m5.temp

import android.content.ContentUris
import android.net.Uri
import com.example.m5.data.SOURCE_LOCAL
import com.example.m5.data.SOURCE_NETEASE
import com.example.m5.data.StandardSongData
import com.example.m5.logic.network.MusicNetwork
import com.example.m5.plugin.PluginConstants
import com.example.m5.plugin.PluginSupport
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * 获取歌曲 URL
 */
object ServiceSongUrl {

    inline fun getUrlProxy(song: StandardSongData, crossinline success: (Any?) -> Unit) {
        getUrl(song) {
            GlobalScope.launch {
                withContext(Dispatchers.Main) {
                    success.invoke(it)
                }
            }
        }
    }

    inline fun getUrl(song: StandardSongData, crossinline success: (Any?) -> Unit) {
        PluginSupport.setSong(song)
        val pluginUrl = PluginSupport.apply(PluginConstants.POINT_SONG_URL)
        if (pluginUrl != null && pluginUrl is String) {
            success.invoke(pluginUrl)
            return
        }
        when (song.source) {
            SOURCE_NETEASE -> {
                GlobalScope.launch {
                    if (song.neteaseInfo?.pl == 0) {
                        success.invoke(null)
                    } else {
                        val url = MusicNetwork.getUrl(song.id.toString(), "Standard").data[0].url
                        success.invoke(url)
                    }
                }
            }

            SOURCE_LOCAL -> {
                val id = song.id?.toLong() ?: 0
                val contentUri: Uri =
                    ContentUris.withAppendedId(
                        android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        id
                    )
                success.invoke(contentUri)
            }
            else -> success.invoke(null)
        }
    }

    private fun getArtistName(artists: List<StandardSongData.StandardArtistData>?): String {
        val sb = StringBuilder()
        artists?.forEach {
            if (sb.isNotEmpty()) {
                sb.append(" ")
            }
            sb.append(it.name)
        }
        return sb.toString()
    }

}