package com.example.contacts.domain.data.update

import com.example.contacts.data.Repository
import javax.inject.Inject

class UpdateUserUserCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(id_user : Int,firstName: String, lastName: String, email: String, job: String){
        repository.updateUser(id_user = id_user, firstName = firstName, lastName = lastName, email = email, job= job)

    }
}