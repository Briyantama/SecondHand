package com.binar.secondhand.ui.bagian_akun.edit_akun

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.secondhand.data.api.model.auth.user.GetAuthResponse
import com.binar.secondhand.data.api.model.auth.user.PutAuthResponse
import com.binar.secondhand.data.repository.Repository
import com.binar.secondhand.data.resource.Resource
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class UbahAkunViewModel(private val repository: Repository) : ViewModel() {

    private val _authPutResponse = MutableLiveData<Resource<Response<PutAuthResponse>>>()
    internal val authPutResponse: LiveData<Resource<Response<PutAuthResponse>>> get() = _authPutResponse

    private val _authGetResponse = MutableLiveData<Resource<Response<GetAuthResponse>>>()
    internal val authGetResponse: LiveData<Resource<Response<GetAuthResponse>>> get() = _authGetResponse

    internal fun putAuth(
        fullname: RequestBody,
        email: RequestBody?= null,
        password: RequestBody?= null,
        phone_number: RequestBody,
        address: RequestBody,
        city: RequestBody,
        image: MultipartBody.Part?
    ) {
        viewModelScope.launch {
            _authPutResponse.postValue(Resource.loading())
            try {
                val dataAuth = Resource.success(
                    repository.putAuth(
                        fullname,
                        email,
                        password,
                        phone_number,
                        address,
                        city,
                        image
                    )
                )
                _authPutResponse.postValue(dataAuth)
            } catch (exp: Exception) {
                _authPutResponse.postValue(Resource
                    .error(exp.localizedMessage ?: "Error occured"))
            }
        }
    }

    internal fun getAuth() {
        viewModelScope.launch {
            _authGetResponse.postValue(Resource.loading())
            try {
                val dataAuth = Resource.success(repository.getAuth())
                _authGetResponse.postValue(dataAuth)
            } catch (exp: Exception) {
                _authGetResponse.postValue(Resource
                    .error(exp.localizedMessage ?: "Error occured"))
            }
        }
    }

}