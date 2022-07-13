package com.binar.secondhand.data.api.model.buyer.orderid.put


import com.google.gson.annotations.SerializedName

data class PutOrderIdRequest(
    @SerializedName("product_id")
    val productId: Int,
    @SerializedName("bid_price")
    val bidPrice: Int
)