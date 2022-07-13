package com.binar.secondhand.data.api.model.buyer.order.post

import com.google.gson.annotations.SerializedName

data class PostOrderRequest
    (
    @SerializedName("product_id")
    val productId: Int,
    @SerializedName("bid_price")
    val bidPrice: String
    )