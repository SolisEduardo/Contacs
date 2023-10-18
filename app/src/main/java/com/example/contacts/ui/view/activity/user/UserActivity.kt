package com.example.contacts.ui.view.activity.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.example.contacts.R
import com.example.contacts.databinding.ActivityUserBinding
import com.example.contacts.ui.view.fragment.crud.ListaUserFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserActivity : AppCompatActivity() {
    private lateinit var binding : ActivityUserBinding
    private val fragment = ListaUserFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frameUser, fragment, "fragment_List_User")
        ft.addToBackStack(null)
        ft.commit()
    }
}