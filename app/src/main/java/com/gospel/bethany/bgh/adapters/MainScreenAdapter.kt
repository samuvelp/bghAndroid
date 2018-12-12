package com.gospel.bethany.bgh.adapters


import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.gospel.bethany.bgh.R
import com.gospel.bethany.bgh.fragments.sermonPlayerFragment.SongPlayingFragment
import com.gospel.bethany.bgh.playerHelper.SermonPlayerHelper
import com.gospel.bethany.bgh.utils.Songs
import java.io.File


/**
 * A simple [Fragment] subclass.
 */
class MainScreenAdapter(_songDetails: ArrayList<Songs>, _context: Context)
    : RecyclerView.Adapter<MainScreenAdapter.MyViewHolder>() {

    var songDetails: ArrayList<Songs>? = null
    var mContext: Context? = null
    var mStorageReference: StorageReference = FirebaseStorage.getInstance().reference

    init {
        this.songDetails = _songDetails
        this.mContext = _context
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val songObject = songDetails?.get(position)
        holder.trackTitle?.text = songObject?.songTitle
        holder.trackArtist?.text = songObject?.artist
        holder.contentHolder?.setOnClickListener {
            if (SermonPlayerHelper.isSermonAvailableLocal(songObject?.songTitle.toString())) {
                val songPlayingFragment = SongPlayingFragment()
                var args = Bundle()
                args.putString("songArtist", songObject?.artist)
                args.putString("path", SermonPlayerHelper.filePath(songObject?.songTitle.toString()))
                args.putString("songTitle", songObject?.songTitle)
                args.putInt("SongId", songObject?.songId?.toInt() as Int)
                args.putInt("songPosition", position)
                args.putParcelableArrayList("songUrl", songDetails)

                songPlayingFragment.arguments = args

                if (SongPlayingFragment.Statified.mediaPlayer != null && SongPlayingFragment.Statified.mediaPlayer?.isPlaying as Boolean) {
                    SongPlayingFragment.Statified.mediaPlayer?.pause()
                    SongPlayingFragment.Statified.mediaPlayer?.release()
                }

                (mContext as FragmentActivity).supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.details_fragment, songPlayingFragment, "SongPlayingFragment")
                        .addToBackStack("SongPlayingFragment")
                        .commit()
            } else {
                Toast.makeText(mContext, "Download the sermon first", Toast.LENGTH_SHORT).show()
            }

        }
        if (SermonPlayerHelper.isSermonAvailableLocal(songObject?.songTitle.toString())) {
            holder.downloadImage?.visibility = View.INVISIBLE
        } else {
            holder.downloadImage?.visibility = View.VISIBLE
        }
        holder.downloadImage?.setOnClickListener {
            val urlRef: StorageReference = FirebaseStorage.getInstance()
                    .getReferenceFromUrl(songObject?.songUrl.toString())
            urlRef.getFile(createFile(songObject?.songTitle.toString()))
                    .addOnSuccessListener {
                        Toast.makeText(mContext, "downloaded", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener {
                        Toast.makeText(mContext, "Failed", Toast.LENGTH_SHORT).show()
                    }
        }
    }

//    fun isSermonAvailableLocal(fileName: String): Boolean {
//        val sermonFile = File(Environment.getExternalStorageDirectory(), "/bgh/$fileName.mp3")
//        if (sermonFile.exists()) {
//            return true
//        }
//        return false
//    }

    fun createFile(fileName: String): File {
        val sd_main = File(Environment.getExternalStorageDirectory(), "/bgh")
        var success = true
        if (!sd_main.exists()) {
            success = sd_main.mkdir()
        }
        if (success) {
            val sd = File(fileName)

            if (!sd.exists()) {
                success = sd.mkdir()
            }
            if (success) {
                return sd
            }
        }
        return (File(sd_main.absoluteFile, "$fileName.mp3"))
    }

//    fun filePath(fileName: String): String {
//        return File(Environment.getExternalStorageDirectory(), "/bgh/$fileName.mp3").absolutePath.toString()
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent?.context)
                .inflate(R.layout.row_custom_mainscreen_adapter, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        if (songDetails == null)
            return 0

        return (songDetails as ArrayList<Songs>).size
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var trackTitle: TextView? = null
        var trackArtist: TextView? = null
        var contentHolder: RelativeLayout? = null
        var downloadLayout: FrameLayout? = null
        var downloadImage: ImageView? = null
        var downloadProgress: ProgressBar? = null

        init {
            trackTitle = view.findViewById(R.id.trackTitle)
            trackArtist = view.findViewById(R.id.trackArtist)
            contentHolder = view.findViewById<RelativeLayout>(R.id.contentRow)
            downloadLayout = view.findViewById(R.id.download_layout)
            downloadImage = view.findViewById(R.id.download_button)
            downloadProgress = view.findViewById(R.id.download_progress_bar)
        }
    }


}// Required empty public constructor
