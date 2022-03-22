package com.example.contacts.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

//data class Address(
//    val street: String?,
//    val city: String?,
//    val country: String?,
//    @ColumnInfo(name = "post_code")
//    val postCode: Int
//)

@Parcelize
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
) : Parcelable