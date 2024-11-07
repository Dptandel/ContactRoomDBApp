package com.tops.kotlin.contactroomdbapp.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.tops.kotlin.contactroomdbapp.models.Contact

@Dao
interface ContactDao {
    @Insert
    fun insertContact(contact: Contact)

    @Update
    fun updateContact(contact: Contact)

    @Delete
    fun deleteContact(contact: Contact)

    @Query("SELECT * FROM contacts")
    fun getAllContacts(): List<Contact>
}