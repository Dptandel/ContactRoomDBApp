package com.tops.kotlin.contactroomdbapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.tops.kotlin.contactroomdbapp.adapters.ContactAdapter
import com.tops.kotlin.contactroomdbapp.dao.ContactDao
import com.tops.kotlin.contactroomdbapp.databases.AppDatabase
import com.tops.kotlin.contactroomdbapp.databinding.ActivityMainBinding
import com.tops.kotlin.contactroomdbapp.models.Contact

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: AppDatabase
    private lateinit var contacts: List<Contact>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Display Contacts
        db =
            Room.databaseBuilder(this, AppDatabase::class.java, "contacts").allowMainThreadQueries()
                .build()

        binding.rvContacts.layoutManager = LinearLayoutManager(this)
        contacts = db.contactDao().getAllContacts()

        val adapter = ContactAdapter(contacts)
        binding.rvContacts.adapter = adapter

        binding.fabAddContact.setOnClickListener {
            val intent = Intent(this, ContactActivity::class.java)
            startActivity(intent)
        }
    }
}