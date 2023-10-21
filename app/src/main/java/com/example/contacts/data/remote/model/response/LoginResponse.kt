package com.example.contacts.data.remote.model.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @field:SerializedName("token")
    val token: String? = null,
    @field:SerializedName("error")
    val error: String? = null
)
