
package com.example.m5.ui.player

import androidx.lifecycle.ViewModel
import com.example.m5.logic.model.Data

class PlayMusicViewModel: ViewModel() {

    companion object{
        var musicList = ArrayList<Data>()
        var position: Int = -1
    }

}
