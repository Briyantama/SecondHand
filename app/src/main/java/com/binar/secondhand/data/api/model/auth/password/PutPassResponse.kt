package com.binar.secondhand.data.api.model.auth.password


import com.google.gson.annotations.SerializedName

data class PutPassResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("message")
    val message: String
)