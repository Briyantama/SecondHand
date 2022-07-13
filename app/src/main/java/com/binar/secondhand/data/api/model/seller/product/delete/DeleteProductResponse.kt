package com.binar.secondhand.data.api.model.seller.product.delete


import com.google.gson.annotations.SerializedName

data class DeleteProductResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("message")
    val message: String
)