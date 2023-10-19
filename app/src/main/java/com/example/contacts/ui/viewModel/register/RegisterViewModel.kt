package com.example.contacts.ui.viewModel.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contacts.R
import com.example.contacts.core.NetworkState
import com.example.contacts.data.remote.model.request.RegisterRequest
import com.example.contacts.data.remote.model.response.RegisterResponse
import com.example.contacts.domain.register.RegisterUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val registerUserCase: RegisterUserCase) : ViewModel() {

    private var _register = MutableLiveData<RegisterResponse>()
    val register: MutableLiveData<RegisterResponse>
        get() = _register

    private val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading
    fun registerUser(registerRequest: RegisterRequest) {
        _isLoading.postValue(true)
        viewModelScope.launch {
            when(val response =registerUserCase.invoke(registerRequest)){
                is NetworkState.Success -> {
                    _register.postValue(response.data!!)
                    _isLoading.postValue(false)
                    _message.postValue("200")
                }
                is NetworkState.Error -> {
                    if (response.response.code() ==400) {
                        _message.postValue("400")
                        _isLoading.postValue(true)
                    } else {
                        _isLoading.postValue(true)
                    }
                }
            }
        }
    }

}