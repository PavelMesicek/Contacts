package com.example.contacts.viewmodel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.contacts.model.Contact
import com.example.contacts.repository.ContactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
    private val contactRepository: ContactRepository
) : ViewModel(), LifecycleObserver {

    fun insertContacts(contact: Contact) = CoroutineScope(Dispatchers.IO).launch {
        contactRepository.insertContacts(contact)
    }

    fun updateContacts(contact: Contact) = CoroutineScope(Dispatchers.IO).launch {
        contactRepository.updateContacts(contact)
    }

    fun deleteContacts(contact: Contact) = CoroutineScope(Dispatchers.IO).launch {
        contactRepository.deleteContacts(contact)
    }

    fun getAllContacts() = contactRepository.getAllContacts()

    fun getAllFavorit()  = contactRepository.getAllFavorit()

    fun searchContact(searchQuery: String): LiveData<List<Contact>> = contactRepository.searchContact(searchQuery)
}