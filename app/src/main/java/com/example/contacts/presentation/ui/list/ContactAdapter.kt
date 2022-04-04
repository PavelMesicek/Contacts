package com.example.contacts.presentation.ui.list

import android.app.AlertDialog
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.contacts.R
import com.example.contacts.databinding.ContactPreviewBinding
import com.example.contacts.model.Contact
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

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val currentItem = items[position]
        holder.binding.tvFistName.text = currentItem.firstName
        holder.binding.tvLastName.text = currentItem.lastName
        holder.binding.tvPhone.text = currentItem.phone.toString()
        holder.binding.tvStreet.text = currentItem.address.street
        holder.binding.tvCity.text = currentItem.address.city
        holder.binding.tvPostCode.text = currentItem.address.postCode.toString()
        holder.binding.ivContactPicture.setImageBitmap(currentItem.contactPhoto)

        fun changeBackground() {
            if (currentItem.lastName.endsWith("ova")) {
                holder.binding.contactPreview.setBackgroundColor(Color.parseColor("#E49B83"))
                holder.binding.ibEdit.setBackgroundColor(Color.parseColor("#E49B83"))
                holder.binding.ibFavorit.setBackgroundColor(Color.parseColor("#E49B83"))
                holder.binding.ibDelete.setBackgroundColor(Color.parseColor("#E49B83"))
            } else {
                holder.binding.contactPreview.setBackgroundColor(Color.parseColor("#8DBC57"))
                holder.binding.ibEdit.setBackgroundColor(Color.parseColor("#8DBC57"))
                holder.binding.ibFavorit.setBackgroundColor(Color.parseColor("#8DBC57"))
                holder.binding.ibDelete.setBackgroundColor(Color.parseColor("#8DBC57"))
            }
        }

        fun changeFavoritImageResource() {
            if (currentItem.isFavorit) {
                holder.binding.ibFavorit.setImageResource(R.drawable.ic_favorit)
            } else {
                holder.binding.ibFavorit.setImageResource(
                    R.drawable.ic_add_to_favorit
                )
            }
        }

        changeBackground()
        changeFavoritImageResource()

        val isVisible: Boolean = currentItem.visibility
        holder.binding.expandableLayout.visibility = if (isVisible) View.VISIBLE else View.GONE

        holder.binding.contactPreview.setOnClickListener {
            currentItem.visibility = !currentItem.visibility
            changeBackground()
            notifyItemChanged(position)
            if (currentItem.address.postCode == 0) holder.binding.tvPostCode.visibility =
                View.VISIBLE else View.GONE
        }

        holder.binding.ibFavorit.setOnClickListener {
            currentItem.isFavorit = !currentItem.isFavorit
            contactViewModel.updateContacts(currentItem)
            changeFavoritImageResource()
        }

        holder.binding.ibEdit.setOnClickListener {
            val action =
                AllContactsFragmentDirections.actionAllContactsFragmentToUpdateContactFragment(
                    currentItem
                )
            holder.itemView.findNavController().navigate(action)
        }

        holder.binding.ibDelete.setOnClickListener {
            fun deleteUser() {
                val builder = AlertDialog.Builder(holder.binding.ibDelete.context)
                builder.setPositiveButton("Yes") { _, _ ->
                    contactViewModel.deleteContacts(currentItem)
                    Toast.makeText(
                        holder.binding.ibDelete.context,
                        "Contact ${currentItem.firstName} ${currentItem.lastName} has been deleted",
                        Toast.LENGTH_LONG
                    ).show()
                }
                builder.setNegativeButton("No") { _, _ -> }
                builder.setTitle("Delete ${currentItem.firstName} ${currentItem.lastName}?")
                builder.setMessage("Do you want to delete ${currentItem.firstName} ${currentItem.lastName} from your contact list?")
                builder.create().show()
            }
            deleteUser()
        }
    }
}