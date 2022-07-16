package com.binar.secondhand.helper

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

class ImageFile {
    private var imageUri : Uri? = null
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

    private fun profileImage(fragment: Fragment, context: Context, user : ImageView, view: View) =
            fragment.registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result: ActivityResult ->
                val resultCode = result.resultCode
                val data = result.data
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        val fileUri = data?.data
                        imageUri = fileUri
                        loadImage(fileUri, user, view)
                    }
                    ImagePicker.RESULT_ERROR -> {
                        Toast.makeText(context, ImagePicker.getError(data), Toast.LENGTH_LONG)
                    }
                    else -> {
                    }
                }
            }


    private fun loadImage(uri: Uri?, user : ImageView, view: View) {
        uri?.let {
            Glide.with(view)
                .load(it)
                .into(user)
        }
    }

    fun openImagePicker(fragment: Fragment, context: Context, user : ImageView, view: View) {
        ImagePicker.with(fragment)
            .crop(1f, 1f)
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
                profileImage(fragment, context, user, view).launch(intent)
            }
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

}