package com.example.contacts.ui.view.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import com.example.contacts.R
import com.example.contacts.data.remote.model.request.LoginRequest
import com.example.contacts.databinding.FragmentLoginBinding
import com.example.contacts.ui.view.activity.user.UserActivity
import com.example.contacts.ui.viewModel.login.LoginViewModel
import com.example.contacts.utils.ValidateEditText
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val loginViewModel : LoginViewModel by viewModels()
    private val TAG : String = LoginFragment::class.java.simpleName

    private val fragment = RegisterFragment()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonLogin.setOnClickListener {
            if(ValidateEditText.areEditTextsNotEmpty(binding.editEmail,binding.editPassword)){
                if(ValidateEditText.doesNotContainEmoji(binding.editEmail)){
                    val user = LoginRequest(password = binding.editPassword.text.toString(), email = binding.editEmail.text.toString())
                    loginViewModel.loginUser(user)
                }else{
                    Toast.makeText(requireContext(),"No se permiten emojis",Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(requireContext(),"No dejar espacio en blanco",Toast.LENGTH_SHORT).show()
            }
        }
        loginViewModel.message.observe(this){
            when(it.toString()){
                "200" -> {
                    startActivity(Intent(activity, UserActivity::class.java))
                }
                "400" -> {
                    Log.i(TAG,"ALGO HICISTE MAL")
                }
            }
        }
        loginViewModel.login.observe(this){loginResponse->
            Log.i(TAG,loginResponse.toString())
        }
        loginViewModel.isLoading.observe(this){
            binding.progressDialog.isVisible = it
        }
        binding.txtRegister.setOnClickListener {
            val ft: FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
            ft.replace(R.id.frameMain, fragment, "fragment_login")
            ft.addToBackStack(null)
            ft.commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}