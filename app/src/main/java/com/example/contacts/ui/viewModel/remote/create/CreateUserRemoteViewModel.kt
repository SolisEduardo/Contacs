package com.example.contacts.ui.viewModel.remote.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contacts.core.ApiState
import com.example.contacts.core.NetworkState
import com.example.contacts.data.Repository
import com.example.contacts.data.remote.model.request.CreateUserRequest
import com.example.contacts.data.remote.model.response.CreateUserResponse
import com.example.contacts.data.remote.model.response.RegisterResponse
import com.example.contacts.domain.remote.create.CreateUserRemoteUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class CreateUserRemoteViewModel @Inject constructor(private val createUserRemoteUserCase: CreateUserRemoteUserCase) : ViewModel(){
    private var _create = MutableLiveData<CreateUserResponse>()
    val create: MutableLiveData<CreateUserResponse>
        get() = _create

    private val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading
    suspend fun createUserRemote(createUserRequest: CreateUserRequest) {
        _isLoading.postValue(true)
        viewModelScope.launch {
            when(val response =createUserRemoteUserCase.invoke(createUserRequest)  ){
                is NetworkState.Success -> {
                    _create.postValue(response.data!!)
                    _isLoading.postValue(false)
                    _message.postValue("201")
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