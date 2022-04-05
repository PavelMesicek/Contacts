package com.example.contacts.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.contacts.model.Contact

@Dao
interface ContactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContacts(contact: Contact)

    @Update
    suspend fun updateContacts(contact: Contact)

    @Delete
    suspend fun deleteContacts(contact: Contact)

    @Query("SELECT * FROM contact_table ORDER BY first_name")
    fun getAllContacts(): LiveData<List<Contact>>

    @Query("SELECT * FROM contact_table WHERE isFavorit == 1")
    fun getAllFavorit(): LiveData<List<Contact>>

    @Query("SELECT * FROM contact_table WHERE first_name LIKE :searchQuery OR last_name LIKE :searchQuery")
    fun searchContact(searchQuery: String): LiveData<List<Contact>>
}