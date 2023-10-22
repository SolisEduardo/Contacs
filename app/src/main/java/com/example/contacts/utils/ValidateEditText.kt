package com.example.contacts.utils

import android.widget.EditText
import java.util.regex.Pattern

object ValidateEditText {
    fun areEditTextsNotEmpty(editText1: EditText, editText2: EditText): Boolean {
        val text1 = editText1.text.toString().trim()
        val text2 = editText2.text.toString().trim()

        return text1.isNotEmpty() && text2.isNotEmpty()
    }
    fun areCreateUser(editText1: EditText, editText2: EditText,editText3: EditText,editText4: EditText ): Boolean {
        val text1 = editText1.text.toString().trim()
        val text2 = editText2.text.toString().trim()
        val text3 = editText3.text.toString().trim()
        val text4 = editText4.text.toString().trim()

        return text1.isNotEmpty() && text2.isNotEmpty() && text3.isNotEmpty() && text4.isNotEmpty()
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
     fun todosEditTextsSinEmojis(editTexts: Array<EditText>): Boolean {
        for (editText in editTexts) {
            val text = editText.text.toString()
            if (contieneEmojis(text)) {
                return false
            }
        }
        return true
    }

    private fun contieneEmojis(text: String): Boolean {
        val emojiPattern = Regex("[\\x{1F600}-\\x{1F64F}]|[\\x{1F300}-\\x{1F5FF}]|[\\x{1F680}-\\x{1F6FF}]|[\\x{1F700}-\\x{1F77F}]|[\\x{1F780}-\\x{1F7FF}]|[\\x{1F800}-\\x{1F8FF}]|[\\x{1F900}-\\x{1F9FF}]|[\\x{1FA00}-\\x{1FA6F}]|[\\x{1FA70}-\\x{1FAFF}]|[\\x{1F000}-\\x{1F0CF}]|[\\x{1F004}-\\x{1F0CF}]|[\\x{1F600}-\\x{1F64F}]|[\\x{1F680}-\\x{1F6C0}]|[\\x{1F170}-\\x{1F251}]|[\\x{1F004}-\\x{1F0CF}]|[\\x{1F910}-\\x{1F96B}]|[\\x{1F980}-\\x{1F9A2}]|[\\x{1F921}-\\x{1F921}]|[\\x{1F600}-\\x{1F64F}]|[\\x{1F300}-\\x{1F5FF}]|[\\x{1F680}-\\x{1F6FF}]|[\\x{1F700}-\\x{1F77F}]|[\\x{1F780}-\\x{1F7FF}]|[\\x{1F800}-\\x{1F8FF}]|[\\x{1F900}-\\x{1F9FF}]|[\\x{1FA00}-\\x{1FA6F}]|[\\x{1FA70}-\\x{1FAFF}]|[\\x{1F000}-\\x{1F0CF}]|[\\x{1F004}-\\x{1F0CF}]|[\\x{1F600}-\\x{1F64F}]|[\\x{1F680}-\\x{1F6C0}]|[\\x{1F170}-\\x{1F251}]|[\\x{1F004}-\\x{1F0CF}]|[\\x{1F910}-\\x{1F96B}]|[\\x{1F980}-\\x{1F9A2}]|[\\x{1F921}-\\x{1F921}]")

        return emojiPattern.containsMatchIn(text)
    }
}