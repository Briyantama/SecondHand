package com.binar.secondhand.ui.bagian_akun.change_pass

import android.content.Context
import android.content.res.Resources
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.secondhand.data.api.model.auth.password.PutPassRequest
import com.binar.secondhand.data.api.model.auth.password.PutPassResponse
import com.binar.secondhand.data.repository.Repository
import com.binar.secondhand.data.resource.Resource
import com.binar.secondhand.helper.Notif
import com.binar.secondhand.helper.ShowPassword
import kotlinx.coroutines.launch
import retrofit2.Response

class ChangePassViewModel(private val repository: Repository): ViewModel() {
    private val _putChangePassResponse = MutableLiveData<Resource<Response<PutPassResponse>>>()
    val putChangePassResponse : LiveData<Resource<Response<PutPassResponse>>> get() = _putChangePassResponse
    private val notif get() = Notif()
    private val showPassword get() = ShowPassword()

    fun putChangePass(request: PutPassRequest){
        viewModelScope.launch {
            _putChangePassResponse.postValue(Resource.loading())
            try {
                val dataPass = Resource.success(repository.putPass(request))
                _putChangePassResponse.postValue(dataPass)
            }catch (t: Exception){
                _putChangePassResponse.postValue(Resource.error(t.message))
            }
        }
    }

    fun show(edit : EditText, eye : ImageView, show : Boolean){
        viewModelScope.launch {
            showPassword.showPassword(show, edit, eye)
        }
    }

    fun toast(message : String, context: Context) {
        viewModelScope.launch {
            notif.showToast(message, context)
        }
    }

    fun snackbarGreen(message : String, view: View, resources: Resources) {
        viewModelScope.launch {
            notif.showSnackbarGreen(message, {}, view, resources)
        }
    }
}