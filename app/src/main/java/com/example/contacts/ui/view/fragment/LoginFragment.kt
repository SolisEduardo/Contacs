package com.example.contacts.ui.view.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.room.util.findColumnIndexBySuffix
import com.example.contacts.R
import com.example.contacts.data.remote.model.request.LoginRequest
import com.example.contacts.databinding.FragmentLoginBinding
import com.example.contacts.ui.view.activity.user.UserActivity
import com.example.contacts.ui.viewModel.remote.login.LoginViewModel
import com.example.contacts.utils.ConstantsUser
import com.example.contacts.utils.ConstantsUser.activeLogin
import com.example.contacts.utils.ConstantsUser.emailLogin
import com.example.contacts.utils.ConstantsUser.passwordLogin
import com.example.contacts.utils.ConstantsUser.rememberLogin
import com.example.contacts.utils.ConstantsUser.sendEmail
import com.example.contacts.utils.ConstantsUser.sendPassword
import com.example.contacts.utils.UtilsMessage
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
    private val bundle = Bundle()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       val shared : SharedPreferences = this.activity!!.getSharedPreferences(ConstantsUser.sharedLogin,Context.MODE_PRIVATE)

        checkLogin(shared)

        binding.buttonLogin.setOnClickListener {
            val edit = arrayOf(binding.editEmail,binding.editPassword)
            if(ValidateEditText.areEditTextsNotEmpty(binding.editEmail,binding.editPassword)){
                if(ValidateEditText.todosEditTextsSinEmojis(edit)){
                    val user = LoginRequest(password = binding.editPassword.text.toString(), email = binding.editEmail.text.toString())
                    loginViewModel.loginUser(user)
                }else{
                    Toast.makeText(requireContext(),getString(R.string.emojis),Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(requireContext(),getString(R.string.editTextBlanck),Toast.LENGTH_SHORT).show()
            }
        }
        loginViewModel.message.observe(this){
            when(it.toString()){
                "200" -> {
                    rememberUser(shared)
                }
                "400" -> {
                    UtilsMessage.showAlertOK(requireContext(),getString(R.string.title_login),getString(R.string.user_not_found))
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
            sendText(shared)
            val ft: FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
            ft.replace(R.id.frameMain, fragment, "fragment_create_user")
            ft.addToBackStack(null)
            ft.commit()
        }
    }

    private fun checkLogin(shared: SharedPreferences) {
        if (shared.getString(activeLogin,"") == "true"){
            startActivity(Intent(activity, UserActivity::class.java))
            activity!!.finish()
        }
    }
    private fun sendText(sp : SharedPreferences){
        val email = binding.editEmail.text.toString()
        val password = binding.editPassword.text.toString()
        with(sp.edit()){
            if (email.isEmpty() && password.isEmpty()){
                putString(sendEmail,"")
                putString(sendPassword,"")
                apply()
            }
            else{
                putString(sendEmail,email)
                putString(sendPassword,password)
                apply()
            }

        }
    }

    private fun rememberUser(sp : SharedPreferences){
        val email = binding.editEmail.text.toString()
        val password = binding.editPassword.text.toString()
        with(sp.edit()){
            putString(emailLogin,email)
            putString(passwordLogin,password)
            putString(activeLogin,"true")
            putString(rememberLogin,"true")
            apply()
        }
        startActivity(Intent(activity, UserActivity::class.java))
        activity!!.finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}