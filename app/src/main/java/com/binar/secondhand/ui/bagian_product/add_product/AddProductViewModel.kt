package com.binar.secondhand.ui.bagian_product.add_product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.secondhand.data.api.model.seller.category.get.GetCategoryResponseItem
import com.binar.secondhand.data.api.model.seller.product.post.PostProductResponse
import com.binar.secondhand.data.repository.Repository
import com.binar.secondhand.data.resource.Resource
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class AddProductViewModel(private val repository: Repository): ViewModel() {

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

    private val _category = MutableLiveData<Resource<List<GetCategoryResponseItem>>>()
    val category : LiveData<Resource<List<GetCategoryResponseItem>>> get() = _category

    fun getCategory() {
        viewModelScope.launch {
            _category.postValue(Resource.loading())
            try {
                _category.postValue(Resource.success(repository.getCategoryItem()))
            } catch (exp: Exception) {
                _category.postValue(
                    Resource.error(
                        exp.localizedMessage ?: "Error occured"
                    )
                )
            }
        }
    }

    private var _categoryList = MutableLiveData<List<String>>()
    val categoryList : LiveData<List<String>> get() = _categoryList

    fun addCategory(category: List<String>){
        _categoryList.postValue(category)
    }

}