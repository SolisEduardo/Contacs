package com.example.contacts.data.remote.network

import com.example.contacts.data.remote.model.request.CreateUserRequest
import com.example.contacts.data.remote.model.request.LoginRequest
import com.example.contacts.data.remote.model.request.RegisterRequest
import com.example.contacts.data.remote.model.response.CreateUserResponse
import com.example.contacts.data.remote.model.response.ListUserResponse
import com.example.contacts.data.remote.model.response.LoginResponse
import com.example.contacts.data.remote.model.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("api/register")
    suspend fun registerUser(@Body registerRequest : RegisterRequest): Response<RegisterResponse>

    @POST("api/login")
    suspend fun loginUser(@Body loginRequest : LoginRequest): Response<LoginResponse>

    @POST("api/users")
    suspend fun createUser(@Body createUserRequest: CreateUserRequest): Response<CreateUserResponse>

    @GET("api/users")
    suspend fun listUser(@Query("page")page:Int) : Response<ListUserResponse>
}