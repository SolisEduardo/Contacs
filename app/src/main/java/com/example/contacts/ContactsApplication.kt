package com.example.contacts

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class ContactsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}