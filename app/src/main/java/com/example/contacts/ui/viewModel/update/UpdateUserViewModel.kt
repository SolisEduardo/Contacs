package com.example.contacts.ui.viewModel.update

import androidx.lifecycle.ViewModel
import com.example.contacts.domain.update.UpdateUserUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UpdateUserViewModel @Inject constructor(private val updateUserUserCase: UpdateUserUserCase): ViewModel(){
    suspend fun updateUser(id_user : Int,firstName: String, lastName: String, email: String){
        updateUserUserCase.invoke(id_user = id_user, firstName = firstName, lastName = lastName, email = email)
    }
}