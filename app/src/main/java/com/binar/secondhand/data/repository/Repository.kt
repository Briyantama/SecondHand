package com.binar.secondhand.data.repository

import com.binar.secondhand.data.api.model.auth.password.PutPassRequest
import com.binar.secondhand.data.api.service.ApiHelper
import okhttp3.MultipartBody
import okhttp3.RequestBody
import com.binar.secondhand.data.api.model.buyer.order.post.PostOrderRequest

class Repository(private val apiHelper: ApiHelper) {

    suspend fun getAuth() = apiHelper.getAuth()

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
    suspend fun getProductId(id: Int) = apiHelper.getProductId(id)
    suspend fun getProductDetail(productId: Int) = apiHelper.getProductDetail(productId)
    suspend fun getUserProfile(userId: Int) = apiHelper.getUserProfile(userId)
    suspend fun postBuyerOrder(request: PostOrderRequest) = apiHelper.postBuyerOrder(request)
    suspend fun postProduct(
        name: RequestBody,
        description: RequestBody,
        base_price: RequestBody,
        category_ids: RequestBody,
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
}