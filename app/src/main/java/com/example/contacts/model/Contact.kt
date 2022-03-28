package com.example.contacts.model

import android.graphics.Bitmap
import android.os.Parcelable
import android.text.Editable
import androidx.room.*
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

data class Address(
    val street: String?,
    val city: String?,
    @ColumnInfo(name = "post_code")
    val postCode: Int?
)

@Parcelize
@Entity(tableName = "contact_table")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val id: Int = -1,
    @ColumnInfo(name = "first_name")
    val firstName: String = "",
    @ColumnInfo(name = "last_name")
    val lastName: String = "",
    @Embedded
    val address: @RawValue Address?,
    val phone: Int = -1,
    val isFavorit: Boolean = false,
    val contactPhoto: Bitmap? = null
) : Parcelable