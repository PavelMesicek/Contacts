package com.example.contacts.utils

class HelperUtils {

    companion object {
        fun areInputValid(firstName: String, lastName: String, phone: String) =
            firstName.isNotEmpty() && lastName.isNotEmpty() && phone.isNotEmpty()
    }
}