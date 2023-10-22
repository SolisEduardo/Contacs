package com.example.contacts.domain.model

import com.example.contacts.data.data.entities.UserEntities
import com.example.contacts.data.remote.model.response.DataItem

data class User(
    val lastName: String,

    val id: Int,

    val avatar: String,

    val firstName: String,

    val email: String,

    val job : String

)
fun DataItem.toDomain()  = User(lastName = lastName!!, id = id!!,avatar=avatar!!, firstName = firstName!!, email = email!!, job = "")
fun UserEntities.toDomain() =  User(lastName = lastName!!, id = id!!,avatar=avatar!!, firstName = firstName!!, email = email!!, job = job!!)
