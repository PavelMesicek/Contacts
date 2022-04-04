package com.example.contacts.presentation.ui.list

import android.app.AlertDialog
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.contacts.R
import com.example.contacts.databinding.ContactPreviewBinding
import com.example.contacts.model.Contact
import com.example.contacts.presentation.ui.InfoDialog.Companion.showToast
import com.example.contacts.viewmodel.ContactViewModel

class ContactAdapter(
    var items: List<Contact>,
    private val contactViewModel: ContactViewModel
) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    class ContactViewHolder(val binding: ContactPreviewBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactViewHolder {
        return ContactViewHolder(
            ContactPreviewBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = items[position]
        setBindingFields(holder, contact)

        changeBackground(contact, holder)
        changeFavoritImageResource(contact, holder)

        holder.binding.expandableLayout.visibility =
            if (contact.visibility) View.VISIBLE else View.GONE

        holder.binding.contactPreview.setOnClickListener {
            contact.visibility = !contact.visibility
            changeBackground(contact, holder)
            notifyItemChanged(position)
        }

        holder.binding.ibFavorit.setOnClickListener {
            contact.isFavorit = !contact.isFavorit
            contactViewModel.updateContacts(contact)
            changeFavoritImageResource(contact, holder)
        }

        holder.binding.ibEdit.setOnClickListener {
            val action = AllContactsFragmentDirections
                .actionAllContactsFragmentToUpdateContactFragment(contact)
            holder.itemView.findNavController().navigate(action)
        }

        holder.binding.ibDelete.setOnClickListener {
            deleteUser(contact, holder)
        }
    }

    private fun setBindingFields(holder: ContactViewHolder, contact: Contact) {
        holder.binding.tvFistName.text = contact.firstName
        holder.binding.tvLastName.text = contact.lastName
        holder.binding.tvPhone.text = contact.phone
        holder.binding.tvStreet.text = contact.address.street
        holder.binding.tvCity.text = contact.address.city
        holder.binding.tvPostCode.text = contact.address.postCode.toString()
        holder.binding.ivContactPicture.setImageBitmap(contact.contactPhoto)
    }

    private fun changeBackground(contact: Contact, holder: ContactViewHolder) {
        if (contact.lastName.endsWith("ova")) {
            setThemeForFemale(holder)
        } else {
            setThemeForMale(holder)
        }
    }

    private fun setThemeForMale(holder: ContactViewHolder) {
        holder.binding.contactPreview.setBackgroundColor(Color.parseColor("#8DBC57"))
        holder.binding.ibEdit.setBackgroundColor(Color.parseColor("#8DBC57"))
        holder.binding.ibFavorit.setBackgroundColor(Color.parseColor("#8DBC57"))
        holder.binding.ibDelete.setBackgroundColor(Color.parseColor("#8DBC57"))
    }

    private fun setThemeForFemale(holder: ContactViewHolder) {
        holder.binding.contactPreview.setBackgroundColor(Color.parseColor("#E49B83"))
        holder.binding.ibEdit.setBackgroundColor(Color.parseColor("#E49B83"))
        holder.binding.ibFavorit.setBackgroundColor(Color.parseColor("#E49B83"))
        holder.binding.ibDelete.setBackgroundColor(Color.parseColor("#E49B83"))
    }

    private fun changeFavoritImageResource(contact: Contact, holder: ContactViewHolder) {
        if (contact.isFavorit) holder.binding.ibFavorit.setImageResource(R.drawable.ic_favorit)
        else holder.binding.ibFavorit.setImageResource(R.drawable.ic_add_to_favorit)
    }

    private fun deleteUser(contact: Contact, holder: ContactViewHolder) {
        val builder = AlertDialog.Builder(holder.binding.ibDelete.context)
        builder.setPositiveButton("Yes") { _, _ ->
            contactViewModel.deleteContacts(contact)
            showToast(
                holder.binding.ibDelete.context,
                "Contact ${contact.firstName} ${contact.lastName} has been deleted"
            )
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete ${contact.firstName} ${contact.lastName}?")
        builder.setMessage("Do you want to delete ${contact.firstName} ${contact.lastName} from your contact list?")
        builder.create().show()
    }
}