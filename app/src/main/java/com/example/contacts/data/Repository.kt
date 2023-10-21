package com.example.contacts.data

import android.util.Log
import com.example.contacts.core.NetworkState
import com.example.contacts.data.data.dao.UserDao
import com.example.contacts.data.data.entities.UserEntities
import com.example.contacts.data.remote.model.request.LoginRequest
import com.example.contacts.data.remote.model.request.RegisterRequest
import com.example.contacts.data.remote.model.response.LoginResponse
import com.example.contacts.data.remote.model.response.RegisterResponse
import com.example.contacts.data.remote.network.Service
import com.example.contacts.domain.model.User
import com.example.contacts.domain.model.toDomain
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(private val service: Service, private val dao: UserDao) {

    private val TAG: String = Repository::class.java.simpleName

    //Login
    suspend fun login(request: LoginRequest): NetworkState<LoginResponse> {
        val response = service.loginUser(request)
        Log.i(TAG, response.toString())
        return response
    }

    //Register
    suspend fun register(request: RegisterRequest): NetworkState<RegisterResponse> {
        val response = service.registerUser(request)
        Log.i(TAG, response.toString())
        return response
    }

    //List
    suspend fun getAllUserFromApi(): List<User> {
        val response = service.getUser()
        return response.data!!.map {
            it!!.toDomain()
        }
    }
    suspend fun getAllUserFromData(): List<User> {
        val response = dao.getAllUser()
        return response.map {
            it.toDomain()
        } }

    suspend fun insertUser(user: List<UserEntities>) {
        dao.insertUser(user)
    }


    suspend fun insertUserForMe(userEntities: UserEntities) {
        dao.insertNotes(userEntities)
    }

    suspend fun searchUser(id_user: Int) :User{
       val response= dao.searchUser(id_user)
        return response.toDomain()

    }

    suspend fun updateUser(id_user: Int, firstName: String, lastName: String, email: String){
        dao.updateUser(id =id_user,firstName = firstName, lastName = lastName, email = email)
    }

    suspend fun deleteUser(id_user: Int){
        dao.deleteSpecificUser(id = id_user)
    }
}


