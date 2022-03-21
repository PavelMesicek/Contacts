package com.example.contacts.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ContactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContacts(contact: Contact)

    @Update
    suspend fun updateContacts(contact: Contact)

    @Delete
    suspend fun deleteContacts(contact: Contact)

    @Query("SELECT * FROM contact_table")
    fun getAllContacts() : LiveData<List<Contact>>

    @Query("SELECT * FROM contact_table WHERE id = :id")
    fun getContactById(id: Int): Contact?

    @Query("SELECT * FROM contact_table WHERE first_name = :first")
    fun findByFirstName(first: String): Contact
}