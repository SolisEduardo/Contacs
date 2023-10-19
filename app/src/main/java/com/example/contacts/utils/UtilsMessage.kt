package com.example.contacts.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog

object UtilsMessage {
    fun showAlertDelete(context: Context, title: String,mensaje: String){
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(mensaje)
        builder.setPositiveButton("Aceptar") { dimiss, _ ->
            dimiss.dismiss()
        }
        builder.setNegativeButton("Cancelar") { dimiss, _ ->
            dimiss.dismiss()
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }
    fun showAlertOK(context: Context, title: String,mensaje: String){
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(mensaje)
        builder.setPositiveButton("Aceptar") { dimiss, _ ->
            dimiss.dismiss()
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }
}