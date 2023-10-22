package com.example.contacts.domain.data.search

import com.example.contacts.data.Repository
import com.example.contacts.data.data.entities.toDataBase
import com.example.contacts.domain.model.User
import javax.inject.Inject

class SearchUser @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(id_user : Int): User{
        val response = repository.searchUser(id_user)
        return response

    }
}