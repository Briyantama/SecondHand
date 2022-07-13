package com.binar.secondhand.data.api.model.auth.password

import com.google.gson.annotations.SerializedName

class PutPassRequest (
        @SerializedName("current_password")
        val currentPassword: String,
        @SerializedName("new_password")
        val newPassword: String,
        @SerializedName("confirm_password")
        val confirmPassword: String
)