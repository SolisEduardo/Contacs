package com.example.contacts.utils

import android.widget.EditText

object DeleteEdiText {
    fun deleteText(name : EditText, lastName: EditText, email: EditText,job: EditText){
        name.setText("")
        lastName.setText("")
        email.setText("")
        job.setText("")
    }
}