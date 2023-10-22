package com.example.contacts.domain.remote.register

import com.example.contacts.core.NetworkState
import com.example.contacts.data.Repository
import com.example.contacts.data.remote.model.request.RegisterRequest
import com.example.contacts.data.remote.model.response.RegisterResponse
import javax.inject.Inject

class RegisterUserCase @Inject constructor(private val repository: Repository) {
    private val TAG : String = RegisterUserCase::class.java.simpleName

    suspend operator fun invoke(registerRequest: RegisterRequest): NetworkState<RegisterResponse> {

        return repository.register(registerRequest)
    }
}