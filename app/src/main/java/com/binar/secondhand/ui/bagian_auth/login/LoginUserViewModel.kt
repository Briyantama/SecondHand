package com.binar.secondhand.ui.bagian_auth.login

import android.content.Context
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.secondhand.data.api.model.auth.login.PostLoginRequest
import com.binar.secondhand.data.api.model.auth.login.PostLoginResponse
import com.binar.secondhand.data.repository.LoginRepository
import com.binar.secondhand.data.resource.Resource
import com.binar.secondhand.helper.Notif
import com.binar.secondhand.helper.ShowPassword
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginUserViewModel(private val repository: LoginRepository): ViewModel() {

        private val _loginPostResponse = MutableLiveData<Resource<Response<PostLoginResponse>>>()
        val loginPostResponse: LiveData<Resource<Response<PostLoginResponse>>> get() = _loginPostResponse
        private val showPass get() = ShowPassword()
        private val notif get() = Notif()

        fun postLogin(request: PostLoginRequest){
                viewModelScope.launch {
                        _loginPostResponse.postValue(Resource.loading())
                        try {
                                val dataExample = Resource.success(repository.postLogin(request))
                                _loginPostResponse.postValue(dataExample)
                        }catch (exp: Exception){
                                _loginPostResponse.postValue(Resource.error(exp.localizedMessage ?: "Error occured"))
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

        fun snackbarWithAction(message : String, actionText : String, view: View, action: () -> Any) {
                viewModelScope.launch {
                        notif.showSnackbarWithAction(message, actionText, view, action)
                }
        }
}