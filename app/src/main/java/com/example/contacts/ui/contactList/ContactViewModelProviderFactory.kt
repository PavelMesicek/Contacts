package com.example.contacts.ui.contactList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.contacts.repository.ContactRepository

class ContactViewModelProviderFactory(private val contactRepository: ContactRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ContactViewModel(contactRepository) as T
    }
}