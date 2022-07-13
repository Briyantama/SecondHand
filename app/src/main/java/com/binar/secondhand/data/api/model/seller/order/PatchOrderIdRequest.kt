package com.binar.secondhand.data.api.model.seller.order


import com.google.gson.annotations.SerializedName

data class PatchOrderIdRequest(
    @SerializedName("status")
    val status: String
)