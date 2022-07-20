package com.binar.secondhand.data.local.room.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataPreview(
    val name : String,
    val price : String,
    val location : String,
    val desc : String,
    val image : Uri,
    val category: String,
    val categoryId: List<Int>
) : Parcelable