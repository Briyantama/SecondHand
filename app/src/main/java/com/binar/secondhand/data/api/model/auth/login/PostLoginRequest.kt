package com.binar.secondhand.data.api.model.auth.login


import com.google.gson.annotations.SerializedName

data class PostLoginRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)