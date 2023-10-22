package com.example.contacts.domain.remote.create

import com.example.contacts.core.NetworkState
import com.example.contacts.data.Repository
import com.example.contacts.data.remote.model.request.CreateUserRequest
import com.example.contacts.data.remote.model.response.CreateUserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CreateUserRemoteUserCase @Inject constructor(private val repository: Repository){
    private val TAG : String = CreateUserRemoteUserCase::class.java.simpleName

    suspend operator fun invoke(createUserRequest: CreateUserRequest) : NetworkState<CreateUserResponse>{
        return repository.createUser(createUserRequest)
    }

}