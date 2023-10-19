package com.example.contacts.utils

import android.widget.EditText
import java.util.regex.Pattern

object ValidateEditText {
    fun areEditTextsNotEmpty(editText1: EditText, editText2: EditText): Boolean {
        val text1 = editText1.text.toString().trim()
        val text2 = editText2.text.toString().trim()

        return text1.isNotEmpty() && text2.isNotEmpty()
    }
    fun doesNotContainEmoji(editText: EditText): Boolean {
        val text = editText.text.toString()
        val regex = Regex(".*[\\uD83C-\\uDBFF\\uDC00-\\uDFFF].*")

        return !regex.matches(text)
    }
    fun isGmailFormat(text: String): Boolean {
        val gmailRegex = "^[a-zA-Z0-9._%+-]+@gmail\\.com$"
        val pattern = Pattern.compile(gmailRegex, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(text)
        return matcher.matches()
    }
    fun isEditTextInGmailFormat(editText: String): Boolean {
        val text = editText.toString().trim()
        return isGmailFormat(text)
    }
}