package com.binar.secondhand.ui.add_product

import android.content.Context
import android.content.res.Resources
import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.secondhand.data.api.model.seller.product.post.PostProductResponse
import com.binar.secondhand.data.repository.Repository
import com.binar.secondhand.data.resource.Resource
import com.binar.secondhand.helper.ImageFile
import com.binar.secondhand.helper.Notif
import com.binar.secondhand.helper.Util
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import java.io.File

class AddProductViewModel(private val repository: Repository): ViewModel() {
    private val _sellerPostProduct = MutableLiveData<Resource<Response<PostProductResponse>>>()
    val sellerPostProduct: LiveData<Resource<Response<PostProductResponse>>> get() = _sellerPostProduct
    private val notif get() = Notif()
    private val image get() = ImageFile()
    private val util get() = Util()

    fun postProduct(
        name: RequestBody,
        description: RequestBody,
        base_price: RequestBody,
        category_ids: RequestBody,
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

    internal fun toast(message : String, context: Context) {
        viewModelScope.launch {
            notif.showToast(message, context)
        }
    }

    internal fun snackbarGreen(message : String, view: View, resources: Resources) {
        viewModelScope.launch {
            notif.showSnackbarGreen(message, {}, view, resources)
        }
    }

    internal fun imagePicker(fragment: Fragment, context: Context, user : ImageView, view: View) {
        viewModelScope.launch {
            image.openImagePicker(fragment, context, user, view)
        }
    }

    fun harga(string: String){
        viewModelScope.launch {
            util.harga(string)
        }
    }

    internal fun reduceImage(file: File): File { return image.reduceImageSize(file) }
    internal fun uriToFile(file : Uri, context: Context) : File { return image.uriToFile(file, context) }
}