package com.tops.kotlin.contactroomdbapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.tops.kotlin.contactroomdbapp.MainActivity
import com.tops.kotlin.contactroomdbapp.databases.AppDatabase
import com.tops.kotlin.contactroomdbapp.databinding.ContactItemBinding
import com.tops.kotlin.contactroomdbapp.models.Contact

class ContactAdapter(
    private var contactList: List<Contact>,
    private var onUpdateContact: ((contact: Contact) -> Unit)? = null,
    private var onDeleteContact: ((contact: Contact) -> Unit)? = null
) :
    RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {
    inner class ContactViewHolder(val binding: ContactItemBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding = ContactItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contactList[position]

        holder.binding.tvName.text = contact.name
        holder.binding.tvContact.text = contact.contact
        holder.binding.tvEmail.text = contact.email

        holder.binding.ivDelete.setOnClickListener {
            onDeleteContact?.invoke(contact)
        }

        holder.binding.ivUpdate.setOnClickListener {
            onUpdateContact?.invoke(contact)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateContacts(newContacts: List<Contact>) {
        contactList = newContacts
        notifyDataSetChanged() // Notify RecyclerView to refresh it
    }
}