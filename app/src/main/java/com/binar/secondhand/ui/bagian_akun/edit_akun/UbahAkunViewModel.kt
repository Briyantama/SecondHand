package com.binar.secondhand.ui.bagian_akun.edit_akun

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
import com.binar.secondhand.data.api.model.auth.user.GetAuthResponse
import com.binar.secondhand.data.api.model.auth.user.PutAuthResponse
import com.binar.secondhand.data.repository.Repository
import com.binar.secondhand.data.resource.Resource
import com.binar.secondhand.helper.ImageFile
import com.binar.secondhand.helper.Notif
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import java.io.File

class UbahAkunViewModel(private val repository: Repository) : ViewModel() {

    private val _authPutResponse = MutableLiveData<Resource<Response<PutAuthResponse>>>()
    internal val authPutResponse: LiveData<Resource<Response<PutAuthResponse>>> get() = _authPutResponse

    private val _authGetResponse = MutableLiveData<Resource<Response<GetAuthResponse>>>()
    internal val authGetResponse: LiveData<Resource<Response<GetAuthResponse>>> get() = _authGetResponse

    private val notif get() = Notif()
    private val image get() = ImageFile()

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

    internal fun reduceImage(file: File): File{ return image.reduceImageSize(file) }
    internal fun uriToFile(file : Uri, context: Context) : File{ return image.uriToFile(file, context) }
}