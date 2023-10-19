package com.example.contacts.data.remote.model.request

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @field:SerializedName("email")
    val email: String? = null,
    @field:SerializedName("password")
    val password: String? = null
)