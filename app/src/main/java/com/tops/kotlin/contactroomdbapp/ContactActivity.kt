package com.tops.kotlin.contactroomdbapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.tops.kotlin.contactroomdbapp.databases.AppDatabase
import com.tops.kotlin.contactroomdbapp.databinding.ActivityContactBinding
import com.tops.kotlin.contactroomdbapp.models.Contact

class ContactActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactBinding
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*// Initialize the database
        db = Room.databaseBuilder(this, AppDatabase::class.java, "contacts").allowMainThreadQueries().build()*/

        db = AppDatabase.getDatabase(this)

        // Create New Contact and Save it
        binding.btnSave.setOnClickListener {
            val name = binding.edtName.text.toString()
            val contact = binding.edtContact.text.toString()
            val email = binding.edtEmail.text.toString()

            val newContact = Contact(name = name, email = email, contact = contact)

            // Insert a new contact
            insertContact(newContact)
        }
    }

    private fun insertContact(contact: Contact) {
        // Insert a new contact
        db.contactDao().insertContact(contact)

        //onBackPressedDispatcher.onBackPressed()

        // Clear the input fields
        binding.edtName.text.clear()
        binding.edtContact.text.clear()
        binding.edtEmail.text.clear()

        val intent = Intent()
        intent.putExtra("msg", "Contact Saved Successfully")
        setResult(RESULT_OK, intent)
        finish()

        Toast.makeText(this, "Contact Saved Successfully", Toast.LENGTH_SHORT).show()

        startActivity(Intent(this, MainActivity::class.java))
    }
}