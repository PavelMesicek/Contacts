package com.example.contacts.presentation.ui.update

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.contacts.R
import com.example.contacts.databinding.FragmentNewContactBinding
import com.example.contacts.model.Address
import com.example.contacts.model.Contact
import com.example.contacts.viewmodel.ContactViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewContactFragment : Fragment() {

    private var _binding: FragmentNewContactBinding? = null
    private val binding get() = _binding!!
    private val contactViewModel: ContactViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewContactBinding.inflate(inflater, container, false)

        val getImage = registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) {
            binding.ivContactPicture.setImageURI(it)
        }

        binding.ivContactPicture.setImageResource(R.drawable.contact_default_image)

        binding.ivContactPicture.setOnClickListener {
            getImage.launch("image/*")
        }

        binding.btnAddContact.setOnClickListener {
            insertContactToDatabase()
        }

        return binding.root
    }

    private fun insertContactToDatabase() {
        val firstName = binding.etFirstName.text.toString()
        val lastName = binding.etLastName.text.toString()
        val phone = binding.etPhone.text
        val street = binding.etStreet.text.toString()
        val city = binding.etCity.text.toString()
        val postCode = binding.etPostCode.text
        val contactPhoto = binding.ivContactPicture.drawable.toBitmap()

        if (inputCheck(firstName, lastName, phone)) {
            val address = Address(street, city, Integer.parseInt(postCode.toString()))
            val contact =
                Contact(0, firstName, lastName, address, Integer.parseInt(phone.toString()), false, contactPhoto)
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