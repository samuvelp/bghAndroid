package com.gospel.bethany.bgh.activities.sermon

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.Toolbar
import com.gospel.bethany.bgh.R
import com.gospel.bethany.bgh.fragments.sermonPlayerFragment.MainScreenFragment
import com.gospel.bethany.bgh.fragments.sermonPlayerFragment.SongPlayingFragment

class SermonPlayerActivity : AppCompatActivity(){

    var audioManager: AudioManager? = null


    object Statified{
        var drawerLayout: DrawerLayout? = null      // static variable of type "DrawerLayout"
        var notificationMAnager : NotificationManager? = null
    }

    var trackNotificationBuilder : Notification? = null

    // Auto-generated
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sermon_player)
        // Auto-generated

        // For volume control....the stream to be modified is STREAM_MUSIC
//        setVolumeControlStream(AudioManager.STREAM_MUSIC)
//        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        SermonPlayerActivity.Statified.drawerLayout = findViewById(R.id.drawer_layout)


//        val mainScreenFragment = MainScreenFragment()
//        this.supportFragmentManager
//                .beginTransaction()
//                .replace(R.id.details_fragment, MainScreenFragment(), "MainScreenFragment")
//                .commit()



        val intent = Intent(this@SermonPlayerActivity, SermonPlayerActivity::class.java)
        val preIntent = PendingIntent.getActivity(this@SermonPlayerActivity, System.currentTimeMillis().toInt() as Int, intent, 0)

        trackNotificationBuilder = Notification.Builder(this)
                .setContentTitle("Echo is playing music")
                .setSmallIcon(R.drawable.echo_logo)
                .setContentIntent(preIntent)
                .setOngoing(true)
                .setAutoCancel(true)
                .build()

        Statified.notificationMAnager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onStart() {
        try{
            Statified.notificationMAnager?.cancel(1111)
        }catch (e : Exception){
            e.printStackTrace()
        }
        super.onStart()
    }

    override fun onResume() {
        try{
            Statified.notificationMAnager?.cancel(1111)
        }catch (e : Exception){
            e.printStackTrace()
        }
        super.onResume()
    }

    override fun onStop() {
        try{
            if(SongPlayingFragment.Statified.mediaPlayer?.isPlaying as Boolean){
                Statified.notificationMAnager?.notify(1111, trackNotificationBuilder)
            }
        }catch(e : Exception) {
            e.printStackTrace()
        }
        super.onStop()
    }

    override fun onDestroy() {
        try{
            if(SongPlayingFragment.Statified.mediaPlayer?.isPlaying as Boolean){
                Statified.notificationMAnager?.notify(1111, trackNotificationBuilder)
            }
        }catch(e : Exception) {
            e.printStackTrace()
        }

        //Statified.notificationMAnager?.cancelAll()
        super.onDestroy()
    }
}
