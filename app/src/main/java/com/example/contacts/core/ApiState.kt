package com.example.contacts.core

import com.example.contacts.data.remote.model.response.ListUserResponse
import com.example.contacts.domain.model.User

sealed class ApiState {
    object Loading : ApiState()
    class Failure(val msg: Throwable) : ApiState()
    class Success(val data : List<User>) : ApiState()
    object Empty : ApiState()
}
