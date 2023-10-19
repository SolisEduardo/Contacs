package com.example.contacts.domain.create

import com.example.contacts.data.Repository
import com.example.contacts.data.data.entities.toDataBase
import com.example.contacts.domain.model.User
import javax.inject.Inject

class CreateUserUserCase @Inject constructor(private val repository: Repository) {
    private val TAG : String = CreateUserUserCase::class.java.simpleName
    suspend operator fun invoke(user : User){
        repository.insertUserForMe(user.toDataBase())
    }
}