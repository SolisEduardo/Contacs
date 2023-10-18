package com.example.contacts.ui.viewModel.search

import androidx.lifecycle.ViewModel
import com.example.contacts.domain.login.LoginUserCase
import com.example.contacts.domain.model.User
import com.example.contacts.domain.search.SearchUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchUser: SearchUser) : ViewModel() {
    suspend fun searchUser(id_user : Int): User{
         val response =searchUser.invoke(id_user)
        return response
    }
}