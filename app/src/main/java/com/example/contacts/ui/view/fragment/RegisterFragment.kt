package com.example.contacts.ui.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.contacts.R
import com.example.contacts.databinding.FragmentRegisterBinding
import com.example.contacts.ui.viewModel.register.RegisterViewModel
import com.example.contacts.utils.ValidateEditText
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val registerViewModel : RegisterViewModel by viewModels()

    private val TAG :String = RegisterFragment::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonRegister.setOnClickListener {
            if (ValidateEditText.areEditTextsNotEmpty(binding.editTextUsername, binding.editTextPassword)){
                if (ValidateEditText.isEditTextInGmailFormat(binding.editTextUsername.text.toString())){
                    Log.i(TAG, "ES CORRECTO")
                }
                else{
                    Toast.makeText(requireContext(),"No tiene el formato correcto",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(requireContext(),"No dejar espacio en blanco", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}