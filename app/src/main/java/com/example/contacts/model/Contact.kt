package com.example.contacts.model

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Address(
    val street: String?,
    val city: String?,
    @ColumnInfo(name = "post_code")
    val postCode: Int?
) : Parcelable

@Parcelize
@Entity(tableName = "contact_table")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "first_name")
    val firstName: String,
    @ColumnInfo(name = "last_name")
    val lastName: String,
    @Embedded
    val address: @RawValue Address?,
    val phone: Int,
    val isFavorit: Boolean = false,
    val contactPhoto: Bitmap?,
    var visibility : Boolean = false
) : Parcelable