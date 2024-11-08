package com.tops.kotlin.contactroomdbapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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
    private lateinit var contacts: List<Contact>
    private val REQ_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getContacts()

        binding.fabAddContact.setOnClickListener {
            /*startActivity(Intent(this, ContactActivity::class.java))*/
            startActivityForResult(Intent(this, ContactActivity::class.java), REQ_CODE)
        }

        /*db = Room.databaseBuilder(this, AppDatabase::class.java, "contacts").allowMainThreadQueries().build()

        binding.rvContacts.layoutManager = LinearLayoutManager(this)
        contacts = db.contactDao().getAllContacts()

        val adapter = ContactAdapter(contacts)
        binding.rvContacts.adapter = adapter*/
    }

    private fun getContacts() {
        contacts = AppDatabase.getDatabase(this).contactDao().getAllContacts()
        binding.rvContacts.layoutManager = LinearLayoutManager(this)
        val adapter = ContactAdapter(contacts)
        binding.rvContacts.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQ_CODE && resultCode == RESULT_OK && data != null) {
            getContacts()
            val msg = data.getStringExtra("msg") ?: "No Contacts Found!!!"
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
    }
}