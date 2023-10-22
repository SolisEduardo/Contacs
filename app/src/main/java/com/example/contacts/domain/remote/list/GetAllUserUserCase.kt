package com.example.contacts.domain.remote.list

import android.util.Log
import com.example.contacts.data.Repository
import com.example.contacts.data.data.entities.toDataBase
import com.example.contacts.domain.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAllUserUserCase @Inject constructor(private val repository: Repository) {
    private val TAG: String = GetAllUserUserCase::class.java.simpleName
    suspend operator fun invoke() : Flow<List<User>> = flow{
        val response = repository.getAllUserFromApi()

        if (repository.getAllUserFromData().isEmpty()){
            Log.i(TAG, repository.getAllUserFromData().toString())
            repository.insertUser(response.map {
                it.toDataBase()
            })
        }
        emit(repository.getAllUserFromData())
        Log.i(TAG,repository.getAllUserFromData().toString())
    } .flowOn(Dispatchers.IO)
}