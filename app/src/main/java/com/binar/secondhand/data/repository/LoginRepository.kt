package com.binar.secondhand.data.repository

import com.binar.secondhand.data.api.model.auth.login.PostLoginRequest
import com.binar.secondhand.data.api.model.auth.register.PostRegisterRequest
import com.binar.secondhand.data.api.service.ApiHelper

class LoginRepository(private val apiHelper: ApiHelper) {

    suspend fun postLogin(request: PostLoginRequest) = apiHelper.postLogin(request)

}