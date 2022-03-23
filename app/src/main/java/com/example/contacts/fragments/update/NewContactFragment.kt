package com.example.contacts.fragments.update

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.contacts.databinding.FragmentNewContactBinding
import com.example.contacts.model.Contact
import com.example.contacts.data.ContactDatabase
import com.example.contacts.repository.ContactRepository
import com.example.contacts.viewmodel.ContactViewModel
import com.example.contacts.viewmodel.ContactViewModelProviderFactory

class NewContactFragment : Fragment() {

    private var _binding: FragmentNewContactBinding? = null
    private val binding get() = _binding!!
    private lateinit var contactViewModel: ContactViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewContactBinding.inflate(inflater, container, false)

        val contactRepository = ContactRepository(ContactDatabase(requireContext()))
        val viewModelProviderFactory = ContactViewModelProviderFactory(contactRepository)
        contactViewModel =
            ViewModelProvider(this, viewModelProviderFactory)[ContactViewModel::class.java]

        binding.btnAddContact.setOnClickListener {
            insertContactToDatabase()
        }

        return binding.root
    }

    private fun insertContactToDatabase() {
        val firstName = binding.etFirstName.text.toString()
        val lastName = binding.etLastName.text.toString()
        val phone = binding.etPhone.text

        if (inputCheck(firstName, lastName, phone)) {
            val contact = Contact(0, firstName, lastName, Integer.parseInt(phone.toString()))
            contactViewModel.insertContacts(contact)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
            val action =
                NewContactFragmentDirections.actionNewContactFragmentToAllContactsFragment()
            findNavController().navigate(action)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields!", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun inputCheck(firstName: String, lastName: String, phone: Editable): Boolean {
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && phone.isEmpty())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}