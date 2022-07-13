package com.binar.secondhand.data.repository

import com.binar.secondhand.data.api.service.ApiHelper

class ProductSaleListRepository(private val apiHelper: ApiHelper) {

    suspend fun getSellerProduct() = apiHelper.getSellerProduct()

    suspend fun getAuth() = apiHelper.getAuth()

    suspend fun getNotification() = apiHelper.getNotification()
}