package com.example.contacts.db

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

//data class Address(
//    val street: String?,
//    val city: String?,
//    val country: String?,
//    @ColumnInfo(name = "post_code")
//    val postCode: Int
//)

@Entity(tableName = "contact_table")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "first_name")
    val firstName: String,
    @ColumnInfo(name = "last_name")
    val lastName: String,
//    @Embedded
//    val address: Address?,
    val phone: Int,
)