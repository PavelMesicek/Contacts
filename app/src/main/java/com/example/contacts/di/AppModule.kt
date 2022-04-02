package com.example.contacts.di

import android.content.Context
import androidx.room.Room
import com.example.contacts.data.ContactDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideContactDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        ContactDatabase::class.java,
        "contact_db"
    ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideContactDao(db: ContactDatabase) = db.contactDao()

}