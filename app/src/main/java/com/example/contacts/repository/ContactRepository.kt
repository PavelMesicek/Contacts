package com.example.contacts.repository

import androidx.lifecycle.LiveData
import com.example.contacts.model.Contact
import com.example.contacts.data.ContactDatabase

class ContactRepository(private val db: ContactDatabase) {

    suspend fun insertContacts(contact: Contact) = db.contactDao().insertContacts(contact)

    suspend fun updateContacts(contact: Contact) = db.contactDao().updateContacts(contact)

    suspend fun deleteContacts(contact: Contact) = db.contactDao().deleteContacts(contact)

    fun getAllContacts() : LiveData<List<Contact>> = db.contactDao().getAllContacts()

    fun getContactById(id: Int): Contact? = db.contactDao().getContactById(id)

    fun findByFirstName(first: String): Contact = db.contactDao().findByFirstName(first)
}