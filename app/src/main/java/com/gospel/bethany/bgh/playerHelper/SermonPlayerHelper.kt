package com.gospel.bethany.bgh.playerHelper

import android.os.Environment
import java.io.File

class SermonPlayerHelper {
    companion object {
        fun isSermonAvailableLocal(fileName: String): Boolean {
            val sermonFile = File(Environment.getExternalStorageDirectory(), "/bgh/$fileName.mp3")
            if (sermonFile.exists()) {
                return true
            }
            return false
        }

        fun filePath(fileName: String): String {
            return File(Environment.getExternalStorageDirectory(), "/bgh/$fileName.mp3").absolutePath.toString()
        }
    }

}