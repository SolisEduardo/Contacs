package com.example.contacts.domain.list

import android.util.Log
import com.example.contacts.data.Repository
import com.example.contacts.data.data.entities.toDataBase
import com.example.contacts.domain.model.User
import javax.inject.Inject

class GetAllUserUserCase @Inject constructor(private val repository: Repository) {
    private val TAG: String = GetAllUserUserCase::class.java.simpleName
    suspend operator fun invoke(): List<User> {
        val response = repository.getAllUserFromApi()
        val responseData = repository.getAllUserFromData()
        /*  return if (response.isNotEmpty()){
              Log.i(TAG,response.toString())
              repository.deleteAllUser()
              repository.insertUser(response.map {
                  Log.e("from ",it.toDataBase().toString())
                  it.toDataBase()
              })
              Log.i("from data",repository.getAllUserFromData().toString())
              response
          }else{
              emptyList()
          }*/


        return if (repository.getAllUserFromData().isEmpty()){
            //repository.deleteAllUser()
            Log.i("PRIMERA","DATA")
            repository.insertUser(response.map {
                Log.e("from ",it.toDataBase().toString())
                it.toDataBase()
            })
            repository.getAllUserFromData()
        }else
        {
            Log.i("SEGUNDA","DATA")
            Log.i("SEGUNDA",repository.getAllUserFromData().toString())
            repository.getAllUserFromData()
        }
    }
}