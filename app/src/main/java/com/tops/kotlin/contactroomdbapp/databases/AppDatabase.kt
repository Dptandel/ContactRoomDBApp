package com.tops.kotlin.contactroomdbapp.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tops.kotlin.contactroomdbapp.dao.ContactDao
import com.tops.kotlin.contactroomdbapp.models.Contact

@Database(entities = [Contact::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao
}