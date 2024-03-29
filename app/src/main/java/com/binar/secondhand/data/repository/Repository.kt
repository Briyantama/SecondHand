package com.binar.secondhand.data.repository

import com.binar.secondhand.data.api.model.auth.password.PutPassRequest
import com.binar.secondhand.data.api.model.buyer.order.post.PostOrderRequest
import com.binar.secondhand.data.api.model.seller.order.PatchSellerOrderIdRequest
import com.binar.secondhand.data.api.service.ApiHelper
import okhttp3.MultipartBody
import okhttp3.RequestBody

class Repository(private val apiHelper: ApiHelper) {

    suspend fun getAuth() = apiHelper.getAuth()
    suspend fun getCategoryItem() = apiHelper.getCategoryItem()

    suspend fun putAuth(
        fullname: RequestBody,
        email: RequestBody ?= null,
        password: RequestBody ?= null,
        phone_number: RequestBody,
        address: RequestBody,
        city: RequestBody,
        image: MultipartBody.Part?
    ) = apiHelper.putAuth(
        fullname,
        email,
        password,
        phone_number,
        address,
        city,
        image
    )

    suspend fun putPass(request: PutPassRequest) = apiHelper.putPass(request)

    suspend fun getNotification() = apiHelper.getNotification()
    suspend fun getBuyerOrder() = apiHelper.getBuyerOrder()
    suspend fun getProductId(id: Int) = apiHelper.getProductId(id)
    suspend fun getProductDetail(productId: Int) = apiHelper.getProductDetail(productId)
    suspend fun getUserProfile(userId: Int) = apiHelper.getUserProfile(userId)
    suspend fun postBuyerOrder(requestBuyerOrder: PostOrderRequest) = apiHelper.postBuyerOrder(requestBuyerOrder)
    suspend fun postProduct(
        name: RequestBody,
        description: RequestBody,
        base_price: RequestBody,
        category_ids: List<Int>,
        location: RequestBody,
        image: MultipartBody.Part?
    ) = apiHelper.postProduct(
        name,
        description,
        base_price,
        category_ids,
        location,
        image
    )

    suspend fun getSellerOrderId(id:Int) = apiHelper.getSellerOrderId(id)
    suspend fun patchSellerOrderId(id:Int, request: PatchSellerOrderIdRequest) = apiHelper.patchSellerOrderId(id, request)
}