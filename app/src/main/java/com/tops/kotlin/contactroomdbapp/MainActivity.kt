package com.tops.kotlin.contactroomdbapp

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
    lateinit var adapter: ContactAdapter
    private lateinit var contacts: List<Contact>
    private val REQ_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getContacts()

        binding.rvContacts.layoutManager = LinearLayoutManager(this)
        adapter = ContactAdapter(contacts, onDeleteContact = {
            showDeleteContactDialog(it)
        }, onUpdateContact = {
            val intent = Intent(this, ContactActivity::class.java).apply {
                putExtra("id", it.id)
            }
            startActivityForResult(intent, REQ_CODE)
        })
        binding.rvContacts.adapter = adapter

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

    private fun showDeleteContactDialog(contact: Contact) {
        AlertDialog.Builder(this).apply {
            setTitle("Delete Contact")
            setMessage("Are you sure you want to delete ?")
        }.setPositiveButton("Delete") { dialog, which ->
            deleteContact(contact)
            Toast.makeText(this, "Contact Deleted Successfully", Toast.LENGTH_SHORT).show()
        }.setNegativeButton("Cancel") { dialog, which ->
            dialog.dismiss()
        }.show()
    }

    private fun deleteContact(contact: Contact) {
        AppDatabase.getDatabase(this).contactDao().deleteContact(contact)
        getContacts() // Refresh the list
        adapter.updateContacts(contacts) // Notify the adapter of the new list
    }

    private fun getContacts() {
        contacts = AppDatabase.getDatabase(this).contactDao().getAllContacts()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQ_CODE && resultCode == RESULT_OK && data != null) {
            getContacts() // Refresh the list
            adapter.updateContacts(contacts) // Notify the adapter of the new list
            val msg = data.getStringExtra("msg") ?: "No Contacts Found!!!"
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
    }
}