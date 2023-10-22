package com.example.contacts.ui.viewModel.data.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contacts.core.ApiState
import com.example.contacts.domain.remote.list.GetAllUserUserCase
import com.example.contacts.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel  @Inject constructor(private val getAllUserUserCase: GetAllUserUserCase) : ViewModel() {
    private val _userStateFlow : MutableStateFlow<ApiState>
            = MutableStateFlow(ApiState.Empty)
    val userStateFlow : StateFlow<ApiState> = _userStateFlow

    fun getUserFlow() = viewModelScope.launch {
        _userStateFlow.value = ApiState.Loading
        getAllUserUserCase.invoke().catch {e->
            _userStateFlow.value = ApiState.Failure(e)
        }.collect{data->
            _userStateFlow.value = ApiState.Success(data)
        }
    }
}