package com.binar.secondhand.ui.bagian_auth.register

import android.content.Context
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.secondhand.data.api.model.auth.register.PostRegisterRequest
import com.binar.secondhand.data.api.model.auth.register.PostRegisterResponse
import com.binar.secondhand.data.repository.RegisterRepository
import com.binar.secondhand.data.resource.Resource
import com.binar.secondhand.helper.Notif
import com.binar.secondhand.helper.ShowPassword
import kotlinx.coroutines.launch
import retrofit2.Response

class RegisterUserViewModel(private val repository: RegisterRepository): ViewModel() {

    private val _registerPostResponse = MutableLiveData<Resource<Response<PostRegisterResponse>>>()
    val registerPostResponse: LiveData<Resource<Response<PostRegisterResponse>>> get() = _registerPostResponse
    private val showPass get() = ShowPassword()
    private val notif get() = Notif()

    fun postRegister(request: PostRegisterRequest){
        viewModelScope.launch {
            _registerPostResponse.postValue(Resource.loading())
            try {
                val dataExample = Resource.success(repository.postRegister(request))
                _registerPostResponse.postValue(dataExample)
            }catch (exp: Exception){
                _registerPostResponse.postValue(Resource.error(exp.localizedMessage ?: "Error occured"))
            }
        }
    }

    fun show(edit : EditText, eye : ImageView, show : Boolean){
        viewModelScope.launch {
            showPass.showPassword(show, edit, eye)
        }
    }

    fun toast(message : String, context: Context) {
        viewModelScope.launch {
            notif.showToast(message, context)
        }
    }

    fun snackbar(message : String, view: View) {
        viewModelScope.launch {
            notif.showSnackbar(message, view)
        }
    }
}