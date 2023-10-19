package com.example.contacts.data.remote.model.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("token")
    val token: String? = null,
    @field:SerializedName("error")
    val error: String? = null
)
