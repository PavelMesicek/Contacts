package com.example.contacts.data

import androidx.room.*
import com.example.contacts.model.Contact
import com.example.contacts.model.Converters

@Database(
    entities = [Contact::class],
    version = 8
)
@TypeConverters(Converters::class)
abstract class ContactDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao
}