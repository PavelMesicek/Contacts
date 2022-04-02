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
import androidx.navigation.fragment.navArgs
import com.example.contacts.databinding.FragmentUpdateContactBinding
import com.example.contacts.model.Address
import com.example.contacts.model.Contact
import com.example.contacts.viewmodel.ContactViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateContactFragment : Fragment() {

    private var _binding: FragmentUpdateContactBinding? = null
    private val binding get() = _binding!!
    private val contactViewModel: ContactViewModel by viewModels()
    private val args by navArgs<UpdateContactFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateContactBinding.inflate(inflater, container, false)

        binding.etFirstName.setText(args.currentContact.firstName)
        binding.etLastName.setText(args.currentContact.lastName)
        binding.etPhone.setText(args.currentContact.phone.toString())
        binding.etStreet.setText(args.currentContact.address?.street)
        binding.etCity.setText(args.currentContact.address?.street)
        binding.etPostCode.setText(args.currentContact.address?.postCode.toString())
        binding.ivContactPicture.setImageBitmap(args.currentContact.contactPhoto)

        binding.btnUpdateContact.setOnClickListener {
            updateContactToDatabase()
        }

        val getImage = registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) {
            binding.ivContactPicture.setImageURI(it)
        }

        binding.ivContactPicture.setOnClickListener {
            getImage.launch("image/*")
        }

        return binding.root
    }

    private fun updateContactToDatabase() {
        val firstName = binding.etFirstName.text.toString()
        val lastName = binding.etLastName.text.toString()
        val phone = binding.etPhone.text
        val street = binding.etStreet.text.toString()
        val city = binding.etCity.text.toString()
        val postCode = binding.etPostCode.text
        val contactPhoto = binding.ivContactPicture.drawable.toBitmap()

        if (inputCheck(firstName, lastName, phone)) {
            val address = Address(street, city, Integer.parseInt(postCode.toString()))
            val contact = Contact(
                args.currentContact.id,
                firstName,
                lastName,
                address,
                Integer.parseInt(phone.toString()),
                false,
                contactPhoto
            )
            contactViewModel.updateContacts(contact)
            Toast.makeText(requireContext(), "Successfully updated!", Toast.LENGTH_LONG).show()
            val action =
                UpdateContactFragmentDirections.actionUpdateContactFragmentToAllContactsFragment()
            findNavController().navigate(action)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields!", Toast.LENGTH_LONG)
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