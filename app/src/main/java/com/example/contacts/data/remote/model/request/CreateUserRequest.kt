package com.example.contacts.data.remote.model.request

import com.google.gson.annotations.SerializedName

data class CreateUserRequest (
    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("job")
    val job: String? = null
)