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
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.contacts.R
import com.example.contacts.data.remote.model.request.RegisterRequest
import com.example.contacts.databinding.FragmentRegisterBinding
import com.example.contacts.ui.view.activity.user.UserActivity
import com.example.contacts.ui.viewModel.register.RegisterViewModel
import com.example.contacts.utils.ConstantsUser
import com.example.contacts.utils.UtilsMessage
import com.example.contacts.utils.ValidateEditText
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val registerViewModel: RegisterViewModel by viewModels()

    private val TAG: String = RegisterFragment::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val shared: SharedPreferences = this.activity!!.getSharedPreferences(
            ConstantsUser.sharedLogin,
            Context.MODE_PRIVATE
        )
        binding.buttonRegister.setOnClickListener {
            if (ValidateEditText.areEditTextsNotEmpty(
                    binding.editTextUsername,
                    binding.editTextPassword
                )
            ) {

                val register = RegisterRequest(
                    email = binding.editTextUsername.text.toString(),
                    password = binding.editTextPassword.text.toString()
                )
                registerViewModel.registerUser(register)


            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.editTextBlanck),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        registerViewModel.message.observe(this) {
            when (it.toString()) {
                "200" -> {
                    rememberUser(shared)
                    // startActivity(Intent(activity, UserActivity::class.java))
                }
                "400" -> {
                    UtilsMessage.showAlertOK(
                        requireContext(),
                        getString(R.string.title_login),
                        getString(R.string.error_login)
                    )
                }
            }
        }
        registerViewModel.register.observe(this) { registerResponse ->
            Log.i(TAG, registerResponse.toString())
        }
        registerViewModel.isLoading.observe(this) {
            binding.progressDialog.isVisible = it
        }
    }

    private fun rememberUser(shared: SharedPreferences) {
        val email = binding.editTextUsername.text.toString()
        val password = binding.editTextPassword.text.toString()
        with(shared.edit()) {
            putString(ConstantsUser.emailLogin, email)
            putString(ConstantsUser.passwordLogin, password)
            putString(ConstantsUser.activeLogin, "true")
            putString(ConstantsUser.rememberLogin, "true")
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