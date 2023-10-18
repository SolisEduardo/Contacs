package com.example.contacts.ui.viewModel.delete

import androidx.lifecycle.ViewModel
import com.example.contacts.domain.delete.DeleteUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class DeleteViewModel @Inject constructor(private val deleteUserUseCase: DeleteUserUseCase) : ViewModel() {
    suspend fun deleteUser(id_user : Int){
        deleteUserUseCase.invoke(id_user = id_user)
    }
}