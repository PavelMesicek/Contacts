package com.example.contacts.model

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Address(
    val street: String? = null,
    val city: String? = null,
    @ColumnInfo(name = "post_code")
    val postCode: String? = null
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
    val address: @RawValue Address,
    val phone: String,
    var isFavorit: Boolean = false,
    val contactPhoto: Bitmap? = null,
    var visibility : Boolean = false
) : Parcelable