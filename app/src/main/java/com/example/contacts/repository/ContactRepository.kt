package com.example.contacts.repository

import androidx.lifecycle.LiveData
import com.example.contacts.data.ContactDao
import com.example.contacts.model.Contact
import javax.inject.Inject

class ContactRepository @Inject constructor(
    private val contactDao: ContactDao
) {
    suspend fun insertContacts(contact: Contact) = contactDao.insertContacts(contact)

    suspend fun updateContacts(contact: Contact) = contactDao.updateContacts(contact)

    suspend fun deleteContacts(contact: Contact) = contactDao.deleteContacts(contact)

    fun getAllContacts(): LiveData<List<Contact>> = contactDao.getAllContacts()

    fun getAllFavorit(): LiveData<List<Contact>> = contactDao.getAllFavorit()

    fun searchContact(searchQuery: String): LiveData<List<Contact>> = contactDao.searchContact(searchQuery)
}