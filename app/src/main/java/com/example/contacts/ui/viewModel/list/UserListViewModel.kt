package com.example.contacts.ui.viewModel.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contacts.domain.list.GetAllUserUserCase
import com.example.contacts.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel  @Inject constructor(private val getAllUserUserCase: GetAllUserUserCase) : ViewModel() {
    private var _getUser = MutableLiveData<List<User>>()
    val getUser: MutableLiveData<List<User>>
        get() = _getUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    fun getAllUser() {
        _isLoading.postValue(true)
        viewModelScope.launch {
            viewModelScope.launch {
                val response= getAllUserUserCase.invoke()
                _getUser.postValue(response)
                /*   when (val response = getAllUserUserCase.invoke()) {
                       is NetworkState.Success -> {
                           _getUser.postValue(response.data!!)
                           //_isLoading.postValue(false)
                           _errorMessage.postValue("Exito")
                       }
                       is NetworkState.Error -> {
                           if (response.response.code() != 200) {

                               _errorMessage.postValue("user not found")
                               _isLoading.postValue(false)
                           } else {
                               _isLoading.postValue(true)
                           }
                       }
                   }*/
            }

        }
    }
}