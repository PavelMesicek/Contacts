package com.example.contacts.presentation.ui

import android.content.Context
import android.widget.Toast

class InfoDialog {

    companion object {
        fun showToast(context: Context, message: String) {
            Toast.makeText(
                context,
                message,
                Toast.LENGTH_SHORT,
            ).show()
        }
    }
}