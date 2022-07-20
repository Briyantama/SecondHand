package com.binar.secondhand.ui.bagian_home.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.secondhand.data.api.model.buyer.product.GetProductResponse
import com.binar.secondhand.data.repository.HomeRepository
import com.binar.secondhand.data.resource.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class SearchViewModel(private val repository: HomeRepository) : ViewModel() {

    private val _productResponse = MutableLiveData<Resource<Response<GetProductResponse>>>()
    val productResponse : LiveData<Resource<Response<GetProductResponse>>> get() = _productResponse

    fun getProduct(
        status: String? = null,
        categoryId: Int? = null,
        searchKeyword: String? = null
    ) {
        viewModelScope.launch {
            _productResponse.postValue(Resource.loading())
            try {
                val dataExample = Resource.success(repository.getProduct(
                    status,
                    categoryId,
                    searchKeyword
                ))
                _productResponse.postValue(dataExample)
            } catch (exp: Exception) {
                _productResponse.postValue(
                    Resource.error(
                        exp.localizedMessage ?: "Error occured"
                    )
                )
            }
        }
    }
}