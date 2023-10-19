package com.example.contacts.domain.delete

import com.example.contacts.data.Repository
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(id_user : Int){
        repository.deleteUser(id_user = id_user)
    }
}