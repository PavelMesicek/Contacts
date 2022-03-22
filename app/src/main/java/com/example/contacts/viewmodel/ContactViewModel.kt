package com.example.contacts.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.contacts.model.Contact
import com.example.contacts.repository.ContactRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactViewModel(private val contactRepository: ContactRepository): ViewModel() {

    fun insertContacts(contact: Contact) = CoroutineScope(Dispatchers.IO).launch {
        contactRepository.insertContacts(contact)
    }

    fun updateContacts(contact: Contact) = CoroutineScope(Dispatchers.IO).launch {
        contactRepository.updateContacts(contact)
    }

    fun deleteContacts(contact: Contact) = CoroutineScope(Dispatchers.IO).launch {
        contactRepository.deleteContacts(contact)
    }

    fun getAllContacts() : LiveData<List<Contact>> = contactRepository.getAllContacts()

    fun getContactById(id: Int): Contact? = contactRepository.getContactById(id)

    fun findByFirstName(first: String): Contact = contactRepository.findByFirstName(first)
}