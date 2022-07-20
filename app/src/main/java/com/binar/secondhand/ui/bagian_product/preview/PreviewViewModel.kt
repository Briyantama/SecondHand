package com.binar.secondhand.ui.bagian_product.preview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.secondhand.data.api.model.auth.user.GetAuthResponse
import com.binar.secondhand.data.api.model.seller.product.post.PostProductResponse
import com.binar.secondhand.data.repository.Repository
import com.binar.secondhand.data.resource.Resource
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class PreviewViewModel(private val repository: Repository) : ViewModel() {

    private val _authGetResponse = MutableLiveData<Resource<Response<GetAuthResponse>>>()
    val authGetResponse: LiveData<Resource<Response<GetAuthResponse>>> get() = _authGetResponse

    internal fun getAuth() {
        viewModelScope.launch {
            _authGetResponse.postValue(Resource.loading())
            try {
                val dataAuth = Resource.success(repository.getAuth())
                _authGetResponse.postValue(dataAuth)
            } catch (exp: Exception) {
                _authGetResponse.postValue(Resource.error(exp.localizedMessage ?: "Error occured"))
            }
        }
    }

    private val _sellerPostProduct = MutableLiveData<Resource<Response<PostProductResponse>>>()
    val sellerPostProduct: LiveData<Resource<Response<PostProductResponse>>> get() = _sellerPostProduct

    fun postProduct(
        name: RequestBody,
        description: RequestBody,
        base_price: RequestBody,
        category_ids: List<Int>,
        location: RequestBody,
        image: MultipartBody.Part?
    ){
        viewModelScope.launch {
            _sellerPostProduct.postValue(Resource.loading())
            try {
                val data = repository.postProduct(
                    name,
                    description,
                    base_price,
                    category_ids,
                    location,
                    image
                )
                _sellerPostProduct.postValue(Resource.success(data))
            }
            catch (e: Exception){
                _sellerPostProduct.postValue(Resource.error(e.message.toString()))
            }
        }
    }
}