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
    private var id: Int? = null
    private var mContact: Contact? = null
    private val REQ_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id = intent.getIntExtra("id", -1)

        /*// Initialize the database
        db = Room.databaseBuilder(this, AppDatabase::class.java, "contacts").allowMainThreadQueries().build()*/

        db = AppDatabase.getDatabase(this)

        if (id != -1) {
            // update contact
            getContact(id!!)
            binding.btnSave.text = "Update"
        }

        // Create New Contact and Save it
        binding.btnSave.setOnClickListener {
            val name = binding.edtName.text.toString()
            val contact = binding.edtContact.text.toString()
            val email = binding.edtEmail.text.toString()

            val newContact = Contact(
                name = name,
                email = email,
                contact = contact,
                id = if (mContact != null) id else null
            )

            if (name.isEmpty() || contact.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            } else {
                if (mContact != null) {
                    updateContact(newContact)
                } else {
                    // Insert a new contact
                    insertContact(newContact)
                }
            }
        }
    }

    private fun getContact(id: Int) {
        mContact = AppDatabase.getDatabase(this).contactDao().getContactById(id)

        mContact?.let {
            binding.edtName.setText(it.name)
            binding.edtEmail.setText(it.email)
            binding.edtContact.setText(it.contact)
        }
    }

    private fun updateContact(contact: Contact) {
        db.contactDao().updateContact(contact)

        //onBackPressedDispatcher.onBackPressed()

        val intent = Intent()
        intent.putExtra("msg", "Contact Updated Successfully")
        setResult(RESULT_OK, intent)
        finish()
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
    }
}