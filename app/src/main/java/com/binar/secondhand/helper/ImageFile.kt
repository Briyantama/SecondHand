package com.binar.secondhand.helper

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.github.dhaval2404.imagepicker.ImagePicker
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

class ImageFile {

    private val format = "dd-MMM-yyyy"
    private val timeStamp: String = SimpleDateFormat(
        format,
        Locale.US
    ).format(System.currentTimeMillis())

    private fun createTempFile(context: Context): File {
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(timeStamp, ".jpg", storageDir)
    }

    fun uriToFile(
        selectedImage: Uri,
        context : Context
    )
            : File {
        val contentResolver: ContentResolver = context.contentResolver
        val myFile = createTempFile(context)

        val inputStream = contentResolver.openInputStream(selectedImage) as InputStream
        val outputStream: OutputStream = FileOutputStream(myFile)
        val buf = ByteArray(1024)
        var len: Int
        while (inputStream.read(buf).also { len = it } > 0) {
            outputStream.write(buf, 0, len)
        }
        outputStream.close()
        inputStream.close()

        return myFile
    }

    fun reduceImageSize(file: File): File {
        val bitmap = BitmapFactory.decodeFile(file.path)
        var compressQuality = 100
        var streamLength: Int
        do {
            val bmpStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
            val bmpPicByteArray = bmpStream.toByteArray()
            streamLength = bmpPicByteArray.size
            compressQuality -= 5
        } while (streamLength > 1000000)
        bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))
        return file
    }

    fun startImageResult(view: View, image: ImageView, context: Context, result: ActivityResult, action: (Uri) -> Unit) {
        val resultCode = result.resultCode
        val data = result.data
        when (resultCode) {
            Activity.RESULT_OK -> {
                val fileUri = data?.data
                action(fileUri!!)
                loadImage(fileUri, view, image)
            }
            ImagePicker.RESULT_ERROR -> {
                Toast.makeText(context, ImagePicker.getError(data), Toast.LENGTH_SHORT)
                    .show()
            }
            else -> {
            }
        }
    }

    fun openImagePicker(fragment: Fragment, context: Context, action: (Intent) -> Unit) {
        ImagePicker.with(fragment)
            .crop()
            .saveDir(
                File(
                    context.externalCacheDir,
                    "ImagePicker"
                )
            )
            .compress(1024)
            .maxResultSize(
                1080,
                1080
            )
            .createIntent { intent ->
                action(intent)
            }
    }

    private fun loadImage(uri: Uri?, view: View, image : ImageView) {
        uri?.let {
            Glide.with(view)
                .load(it)
                .centerCrop()
                .apply(RequestOptions.bitmapTransform(RoundedCorners(16)))
                .into(image)
        }
    }
}