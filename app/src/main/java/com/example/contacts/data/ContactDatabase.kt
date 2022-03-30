package com.example.contacts.data

import android.content.Context
import androidx.room.*
import com.example.contacts.model.Contact
import com.example.contacts.model.Converters

@Database(
    entities = [Contact::class],
    version = 5
)
@TypeConverters(Converters::class)
abstract class ContactDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao

    companion object {

        @Volatile
        private var instance: ContactDatabase? = null

        //for synchronizing instance, make sure there is just one single instance at once
        private val LOCK = Any()

        //create when we recreated our instance
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ContactDatabase::class.java,
                "contacts_db"
            ).fallbackToDestructiveMigration().build()
    }
}