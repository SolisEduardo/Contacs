package com.example.contacts.data.remote.network

import android.util.Log
import com.example.contacts.core.NetworkState
import com.example.contacts.data.remote.model.request.CreateUserRequest
import com.example.contacts.data.remote.model.request.LoginRequest
import com.example.contacts.data.remote.model.request.RegisterRequest
import com.example.contacts.data.remote.model.response.CreateUserResponse
import com.example.contacts.data.remote.model.response.ListUserResponse
import com.example.contacts.data.remote.model.response.LoginResponse
import com.example.contacts.data.remote.model.response.RegisterResponse
import javax.inject.Inject

class Service @Inject constructor(private val api : ApiService) {
    private val TAG: String = Service::class.java.simpleName
    //Login
    suspend fun loginUser(request: LoginRequest): NetworkState<LoginResponse> {
        val response = api.loginUser(request)
        Log.i(TAG, response.code().toString())
        return if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null) {
                NetworkState.Success(responseBody)
            } else {
                NetworkState.Error(response)
            }
        } else {
            NetworkState.Error(response)
        }
    }

    //Register
    suspend fun registerUser(request: RegisterRequest): NetworkState<RegisterResponse> {
        val response = api.registerUser(request)
        Log.i(TAG, response.code().toString())
        return if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null) {
                NetworkState.Success(responseBody)
            } else {
                NetworkState.Error(response)
            }
        } else {
            NetworkState.Error(response)
        }
    }

    //createUser
    suspend fun createUser(createUserRequest: CreateUserRequest) :NetworkState< CreateUserResponse>{
        val response = api.createUser(createUserRequest = createUserRequest)
        Log.i(TAG, response.code().toString())
        return if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null) {
                NetworkState.Success(responseBody)
            } else {
                NetworkState.Error(response)
            }
        } else {
            NetworkState.Error(response)
        }
    }

    //list
    suspend fun getUser(): ListUserResponse {
        val response = api.listUser(2)
        Log.i(TAG, response.code().toString())
        return response.body()!!
    }
}