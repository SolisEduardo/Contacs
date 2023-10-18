package com.example.contacts.domain.login

import com.example.contacts.core.NetworkState
import com.example.contacts.data.Repository
import com.example.contacts.data.remote.model.request.LoginRequest
import com.example.contacts.data.remote.model.response.LoginResponse
import javax.inject.Inject

class LoginUserCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(loginRequest: LoginRequest): NetworkState<LoginResponse> {

        return repository.login(loginRequest)
    }
}