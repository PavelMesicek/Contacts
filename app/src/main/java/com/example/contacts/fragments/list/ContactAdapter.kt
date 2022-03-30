package com.example.contacts.fragments.list

import android.app.AlertDialog
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
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
        val firstName: TextView = itemView.findViewById(R.id.tvFistName)
        val lastName: TextView = itemView.findViewById(R.id.tvLastName)
        val phone: TextView = itemView.findViewById(R.id.tvPhone)
        val street: TextView = itemView.findViewById(R.id.tvStreet)
        val city: TextView = itemView.findViewById(R.id.tvCity)
        val postCode: TextView = itemView.findViewById(R.id.tvPostCode)
        val editContact: ImageButton = itemView.findViewById(R.id.ibEdit)
        val deleteContact: ImageButton = itemView.findViewById(R.id.ibDelete)
        val favoriteContact: ImageButton = itemView.findViewById(R.id.ibFavorit)
        val contactPreview: ConstraintLayout = itemView.findViewById(R.id.contactPreview)
        val contactPhoto: ImageView = itemView.findViewById(R.id.ivContactPicture)
        val expandableLayout: ConstraintLayout = itemView.findViewById(R.id.expandableLayout)
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
        holder.street.text = currentItem.address?.street
        holder.city.text = currentItem.address?.city
        holder.postCode.text = currentItem.address?.postCode.toString()
        holder.contactPhoto.setImageBitmap(currentItem.contactPhoto)

        val isVisible : Boolean = currentItem.visibility
        holder.expandableLayout.visibility = if (isVisible) View.VISIBLE else View.GONE

        holder.contactPreview.setOnClickListener {
            currentItem.visibility = !currentItem.visibility
            notifyItemChanged(position)
        }

        if (currentItem.lastName.endsWith("ova")) {
            holder.contactPreview.setBackgroundColor(Color.parseColor("#E49B83"))
        } else {
            holder.contactPreview.setBackgroundColor(Color.parseColor("#8DBC57"))
            holder.editContact.setBackgroundColor(Color.parseColor("#8DBC57"))
            holder.favoriteContact.setBackgroundColor(Color.parseColor("#8DBC57"))
            holder.deleteContact.setBackgroundColor(Color.parseColor("#8DBC57"))
        }

        holder.editContact.setOnClickListener {
            val action =
                AllContactsFragmentDirections.actionAllContactsFragmentToUpdateContactFragment(
                    currentItem
                )
            holder.itemView.findNavController().navigate(action)
        }

        holder.deleteContact.setOnClickListener {
            fun deleteUser() {
                val builder = AlertDialog.Builder(holder.deleteContact.context)
                builder.setPositiveButton("Yes") { _, _ ->
                    contactViewModel.deleteContacts(currentItem)
                    Toast.makeText(
                        holder.deleteContact.context,
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