package com.binar.secondhand.data.local.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SearchHistoryEntity (
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val searchKeyword: String
)