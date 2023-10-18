package com.example.contacts.ui.view.fragment.crud

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.contacts.R
import com.example.contacts.databinding.FragmentCreateUserBinding
import com.example.contacts.domain.model.User
import com.example.contacts.ui.viewModel.create.CreateUserViewModel
import com.example.contacts.utils.DeleteEdiText
import com.example.contacts.utils.EntityID
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.random.Random

@AndroidEntryPoint
class CreateUserFragment : Fragment() {

    private var _binding: FragmentCreateUserBinding? = null
    private val binding get() = _binding!!

    private val createUserViewModel: CreateUserViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonAdd.setOnClickListener {
            val userCreate = User(
                lastName = binding.editTextLastName.text.toString(),
                avatar = "",
                firstName = binding.editTextFirtName.text.toString(),
                email = binding.editTextEmail.text.toString(),
                id = EntityID.generateID()
            )
            GlobalScope.launch {
                createUserViewModel.insertUser(userCreate)
            }
            DeleteEdiText.deleteText(binding.editTextFirtName, binding.editTextLastName, binding.editTextEmail)
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}