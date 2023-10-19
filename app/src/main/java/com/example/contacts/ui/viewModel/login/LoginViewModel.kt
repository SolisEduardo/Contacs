package com.example.contacts.ui.viewModel.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contacts.core.NetworkState
import com.example.contacts.data.remote.model.request.LoginRequest
import com.example.contacts.data.remote.model.response.LoginResponse
import com.example.contacts.domain.login.LoginUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel  @Inject constructor(private val registerUserCase: LoginUserCase) : ViewModel() {
    private val TAG: String = LoginViewModel::class.java.simpleName

    private var _login = MutableLiveData<LoginResponse>()
    val login: MutableLiveData<LoginResponse>
        get() = _login

    private val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun loginUser(loginRequest: LoginRequest) {
        _isLoading.postValue(true)
        viewModelScope.launch {
            when(val response =registerUserCase.invoke(loginRequest)){
                is NetworkState.Success -> {
                    _login.postValue(response.data!!)
                    _isLoading.postValue(false)
                    _message.postValue("200")
                }
                is NetworkState.Error -> {
                    if (response.response.code() == 400) {

                        _message.postValue("400")
                        _isLoading.postValue(false)
                    } else {
                        _isLoading.postValue(true)
                    }
                }
            }
        }
    }
}