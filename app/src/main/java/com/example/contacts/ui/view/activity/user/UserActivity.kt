package com.example.contacts.ui.view.activity.user

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.example.contacts.R
import com.example.contacts.databinding.ActivityUserBinding
import com.example.contacts.ui.view.activity.main.MainActivity
import com.example.contacts.ui.view.fragment.crud.ListaUserFragment
import com.example.contacts.utils.ConstantsUser
import com.example.contacts.utils.ConstantsUser.emailLogin
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope

@AndroidEntryPoint
class UserActivity : AppCompatActivity() {
    private lateinit var binding : ActivityUserBinding
    private val fragment = ListaUserFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sp = getSharedPreferences(ConstantsUser.sharedLogin,Context.MODE_PRIVATE)
        binding.exitIcon.setOnClickListener {
            logOut(sp)
        }
        binding.title.setText(sp.getString(emailLogin,""))
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frameUser, fragment, "fragment_List_User")
        ft.addToBackStack(null)
        ft.commit()
    }

    private fun logOut(sp : SharedPreferences) {

        with(sp.edit()){
            putString(ConstantsUser.activeLogin,"false")
            apply()
        }
        startActivity(Intent(this@UserActivity, MainActivity::class.java))
        finish()
    }
}