package com.example.contacts.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.contacts.R
import com.example.contacts.model.Contact
import com.example.contacts.viewmodel.ContactViewModel

class ContactAdapter(
    var items: List<Contact>,
    private val contactViewModel: ContactViewModel
) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {
    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val firstName : TextView = itemView.findViewById(R.id.tvFistName)
        val lastName : TextView = itemView.findViewById(R.id.tvLastName)
        val phone : TextView = itemView.findViewById(R.id.tvPhone)
        val editContact : ImageButton = itemView.findViewById(R.id.ibEdit)
        val deleteContact : ImageButton = itemView.findViewById(R.id.ibDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.contact_preview, parent, false)
        return ContactViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val currentItem = items[position]
        holder.firstName.text = currentItem.firstName
        holder.lastName.text = currentItem.lastName
        holder.phone.text = currentItem.phone.toString()

        holder.editContact.setOnClickListener {
            val action = AllContactsFragmentDirections.actionAllContactsFragmentToUpdateContactFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }

        holder.deleteContact.setOnClickListener {
            contactViewModel.deleteContacts(currentItem)
        }
    }
}