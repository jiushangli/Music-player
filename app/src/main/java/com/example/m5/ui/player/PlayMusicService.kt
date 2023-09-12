/*package com.example.m5.ui.player
package com.example.m5.ui.player

import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log


class PlayMusicService(): Service(), AudioManager.OnAudioFocusChangeListener {


    private val mBinder = PlayerBinder()

    class PlayerBinder: Binder(){
        fun prepareMediaPlayer(){
            if (Player.mediaPlayer == null){
                Player.mediaPlayer = MediaPlayer()
            }

            try {
                Player.mediaPlayer!!.apply {
                    thread {
                        reset()
                        Log.d("hucheng", "url: ${PlayMusicViewModel.musicList[PlayMusicViewModel.position].url}")
                        setDataSource(PlayMusicViewModel.musicList[PlayMusicViewModel.position].url)
                    }
                    Player.mediaPlayer!!.start()
                }
            }catch (e: Exception){
                return
            }

        }
    }


    //实现接口、父类函数和声明周期函数

    //其他组件通过bindService来绑定Player()的时候被调用
    override fun onBind(p0: Intent?): IBinder {
        return mBinder
    }



    //音频焦点变化时调用
    override fun onAudioFocusChange(p0: Int) {
        TODO("Not yet implemented")
    }

    //创建时被调用
    override fun onCreate() {
        super.onCreate()
        if (Player.mediaPlayer == null)
            Player.mediaPlayer = MediaPlayer()

        //创建前台Service
//        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            val channel = NotificationChannel("my_service", "前台Service通知", NotificationManager.IMPORTANCE_DEFAULT)
//            manager.createNotificationChannel(channel)
//        }
//        val intent = Intent(this, SearchActivity::class.java)
//        val pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
//        val notification = NotificationCompat.Builder(this, "mu_service")
//            .setContentTitle("Player")
//            .setContentText("text")
//            .setContentIntent(pi)
//            .build()
//
//        startForeground(1, notification)

    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (Player.mediaPlayer == null)
            Player.mediaPlayer = MediaPlayer()
        return super.onStartCommand(intent, flags, startId)
    }



}
*/
