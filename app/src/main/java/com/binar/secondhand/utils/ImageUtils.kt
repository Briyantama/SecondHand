package com.binar.secondhand.utils

import android.annotation.SuppressLint
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.io.File
import java.io.FileInputStream
import java.io.IOException

fun ImageView.loadImage(imageSource : String?) {
    Glide.with(context)
        .load(imageSource)
        .into(this)
}

@SuppressLint("LongLogTag")
private fun saveImageString(myfile: File): String? {
    var fileInputStream: FileInputStream? = null
    try {
        Log.d("file_read_send_to_getData_function", myfile.toString())
        fileInputStream = FileInputStream(myfile)
        var i = -1
        val buffer = StringBuffer()
        while (fileInputStream.read().also { i = it } != -1) {
            buffer.append(i.toChar())
        }
        return buffer.toString()
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        if (fileInputStream != null) {
            try {
                fileInputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
    return null
}
