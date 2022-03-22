package com.example.contacts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.contacts.repository.ContactRepository

@Suppress("UNCHECKED_CAST")
class ContactViewModelProviderFactory(private val contactRepository: ContactRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ContactViewModel(contactRepository) as T
    }
}