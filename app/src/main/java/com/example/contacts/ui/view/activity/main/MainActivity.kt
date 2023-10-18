package com.example.contacts.ui.view.activity.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.example.contacts.R
import com.example.contacts.databinding.ActivityMainBinding
import com.example.contacts.ui.view.fragment.LoginFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding :ActivityMainBinding
    private val fragment = LoginFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frameMain, fragment, "fragment_meters")
        ft.addToBackStack(null)
        ft.commit()
    }
}