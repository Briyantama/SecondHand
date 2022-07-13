package com.binar.secondhand.data.repository

import com.binar.secondhand.data.api.model.auth.register.PostRegisterRequest
import com.binar.secondhand.data.api.service.ApiHelper

class RegisterRepository(private val apiHelper: ApiHelper) {

    suspend fun postRegister(request: PostRegisterRequest) = apiHelper.postRegister(request)

}