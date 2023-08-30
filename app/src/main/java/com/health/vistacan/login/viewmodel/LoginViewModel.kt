package com.health.vistacan.login.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.health.vistacan.utils.Utils
import okhttp3.ResponseBody

class LoginViewModel(application: Application):AndroidViewModel(application) {

    val context = application

    private val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading


    private val _onMessageError = MutableLiveData<Int>()
    val onMessageError: LiveData<Int> = _onMessageError

//    private val _loginResponse = MutableLiveData<LoginResponse>()
//    val loginResponse: LiveData<LoginResponse> = _loginResponse



    private val _loginResponseMsg = MutableLiveData<ResponseBody>()
    val loginResponseMsg: LiveData<ResponseBody> = _loginResponseMsg



    fun docall_Login_API(phone: String, password: String) {
        _isViewLoading.postValue(true)
        val baseURL = Utils.getBaseUrl(context)
    }

}