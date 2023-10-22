package com.example.contacts.ui.view.fragment.crud

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.contacts.R
import com.example.contacts.data.remote.model.request.CreateUserRequest
import com.example.contacts.databinding.FragmentCreateUserBinding
import com.example.contacts.domain.model.User
import com.example.contacts.domain.remote.create.CreateUserRemoteUserCase
import com.example.contacts.ui.viewModel.data.create.CreateUserViewModel
import com.example.contacts.ui.viewModel.remote.create.CreateUserRemoteViewModel
import com.example.contacts.utils.DeleteEdiText
import com.example.contacts.utils.EntityID
import com.example.contacts.utils.UtilsMessage
import com.example.contacts.utils.ValidateEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.random.Random

@AndroidEntryPoint
class CreateUserFragment : Fragment() {

    private var _binding: FragmentCreateUserBinding? = null
    private val binding get() = _binding!!

    private val createUserViewModel: CreateUserViewModel by viewModels()
    private  val createUserRemote : CreateUserRemoteViewModel by viewModels()

    private val TAG : String = CreateUserFragment::class.java.simpleName


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        UtilsMessage.showAlertOK(requireContext(),getString(R.string.advertencia),getString(R.string.advertencia_create_user))
        binding.buttonAdd.setOnClickListener {
            val edit = arrayOf(binding.editTextLastName,binding.editTextFirtName, binding.editTextEmail, binding.editTextJob)
            if (ValidateEditText.areCreateUser(binding.editTextLastName,binding.editTextFirtName, binding.editTextEmail, binding.editTextJob)){
                if (ValidateEditText.todosEditTextsSinEmojis(edit)){
                   /* GlobalScope.launch {
                        createUserRemote.createUserRemote(CreateUserRequest(name =binding.editTextFirtName.text.toString(),job = binding.editTextJob.text.toString() ))
                    }*/
                    createUserFun()
                }else{
                    Toast.makeText(requireContext(),"tiene emojis",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(requireContext(),getString(R.string.editTextBlanck), Toast.LENGTH_SHORT).show()
            }
            createUserRemote.create.observe(this){
                Log.i(TAG,it.toString())
            }
            createUserRemote.message.observe(this){
                when(it){
                    "201" -> {
                        createUserFun()
                    }
                }
            }


           /* if (ValidateEditText.areCreateUser(binding.editTextLastName,binding.editTextFirtName, binding.editTextEmail)){
                if (ValidateEditText.isEditTextInGmailFormat(binding.editTextEmail.text.toString())){
                    val userCreate = User(
                        lastName = binding.editTextLastName.text.toString(),
                        avatar = "",
                        firstName = binding.editTextFirtName.text.toString(),
                        email = binding.editTextEmail.text.toString(),
                        id = EntityID.generateID(),
                        job = binding.editTextJob.text.toString()
                    )
                    GlobalScope.launch {
                        createUserViewModel.insertUser(userCreate)
                    }
                    DeleteEdiText.deleteText(binding.editTextFirtName, binding.editTextLastName, binding.editTextEmail)
                    requireActivity().supportFragmentManager.popBackStack()

                }else{
                    Toast.makeText(requireContext(),getString(R.string.gmail_invalido),Toast.LENGTH_SHORT).show()
                }

            }else{
                Toast.makeText(requireContext(),getString(R.string.editTextBlanck), Toast.LENGTH_SHORT).show()
            }*/
        }
    }

    private fun createUserFun() {
        val userCreate = User(
            lastName = binding.editTextLastName.text.toString(),
            avatar = "",
            firstName = binding.editTextFirtName.text.toString(),
            email = binding.editTextEmail.text.toString(),
            id = EntityID.generateID(),
            job = binding.editTextJob.text.toString()
        )
        GlobalScope.launch {
            createUserViewModel.insertUser(userCreate)
        }
        DeleteEdiText.deleteText(binding.editTextFirtName, binding.editTextLastName, binding.editTextEmail, binding.editTextJob)
        requireActivity().supportFragmentManager.popBackStack()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}