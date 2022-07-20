package com.binar.secondhand.ui.bagian_akun.akun

import android.content.Context
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.secondhand.data.api.model.auth.user.GetAuthResponse
import com.binar.secondhand.data.repository.Repository
import com.binar.secondhand.data.resource.Resource
import com.binar.secondhand.helper.Notif
import kotlinx.coroutines.launch
import retrofit2.Response

class AkunViewModel(private val repository: Repository) : ViewModel() {

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

    private val notif get() = Notif()

    internal fun toast(message : String, context: Context) {
        viewModelScope.launch {
            notif.showToast(message, context)
        }
    }

    internal fun snackbar(view: View, message: String){
        viewModelScope.launch {
            notif.showSnackbar(message, view)
        }
    }

    internal fun dialog(
        context: Context,
        judul: String,
        message: String,
        positif: String,
        negatif: String,
        action: () -> Unit
    ){
        viewModelScope.launch {
            notif.dialogPN(context, judul, message, positif, negatif, action)
        }
    }
}