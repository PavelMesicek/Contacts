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

}