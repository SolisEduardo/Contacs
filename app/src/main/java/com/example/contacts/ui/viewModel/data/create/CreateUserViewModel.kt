package com.example.contacts.ui.viewModel.data.create

import androidx.lifecycle.ViewModel
import com.example.contacts.domain.data.create.CreateUserUserCase
import com.example.contacts.domain.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateUserViewModel @Inject constructor(private val createUserUserCase: CreateUserUserCase): ViewModel(){
    suspend fun insertUser(user : User){
        createUserUserCase.invoke(user)
    }
}