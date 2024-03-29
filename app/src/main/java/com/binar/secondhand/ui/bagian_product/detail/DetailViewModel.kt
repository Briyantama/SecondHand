package com.binar.secondhand.ui.bagian_product.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.secondhand.data.api.model.buyer.order.get.GetOrderResponse.GetOrderResponseItem
import com.binar.secondhand.data.api.model.buyer.productid.GetProductIdResponse
import com.binar.secondhand.data.api.model.buyer.productid.UserProduct
import com.binar.secondhand.data.repository.Repository
import com.binar.secondhand.data.resource.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class DetailViewModel(private val repository: Repository): ViewModel(){

    private val _detailProduct = MutableLiveData<Resource<Response<GetProductIdResponse>>>()
    val detailProduct: LiveData<Resource<Response<GetProductIdResponse>>> get() = _detailProduct

    fun getDetailProduct(productId: Int){
        viewModelScope.launch {
            _detailProduct.postValue(Resource.loading())
            try {
                val dataProduct = Resource.success(repository.getProductDetail(productId))
                _detailProduct.postValue(dataProduct)
            }catch (exp: Exception){
                _detailProduct.postValue(Resource.error(exp.localizedMessage ?: "Error occured"))
            }
        }
    }

    private val _getBuyerOrder :  MutableLiveData<Resource<List<GetOrderResponseItem>>> = MutableLiveData()
    val getBuyerOrder : LiveData<Resource<List<GetOrderResponseItem>>> get() = _getBuyerOrder

    fun getBuyerOrder (){
        viewModelScope.launch {
            _getBuyerOrder.postValue(Resource.loading())
            try{
                _getBuyerOrder.postValue(Resource.success(repository.getBuyerOrder()))
            } catch (e:Exception){
                _getBuyerOrder.postValue(Resource.error(e.localizedMessage?:"Error occurred"))
            }
        }
    }

    private val _getProfile = MutableLiveData<Resource<Response<UserProduct>>>()
    val getProfile : LiveData<Resource<Response<UserProduct>>> get() = _getProfile

    fun getUserProfile(userId: Int) {
        viewModelScope.launch {
            _getProfile .postValue(Resource.loading())
            try {
                val dataUser = Resource.success(repository.getUserProfile(userId))
                _getProfile .postValue(dataUser)
            }catch (exp: Exception){
                _getProfile .postValue(Resource.error(exp.localizedMessage ?: "Error occured"))
            }
        }
    }

}