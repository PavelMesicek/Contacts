package com.example.contacts.presentation.ui.update

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.contacts.databinding.FragmentUpdateContactBinding
import com.example.contacts.model.Address
import com.example.contacts.model.Contact
import com.example.contacts.presentation.ui.InfoDialog.Companion.showToast
import com.example.contacts.utils.HelperUtils.Companion.areInputValid
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
        binding.etPhone.setText(args.currentContact.phone)
        binding.etStreet.setText(args.currentContact.address.street)
        binding.etCity.setText(args.currentContact.address.street)
        binding.etPostCode.setText(args.currentContact.address.postCode)
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
        val phone = binding.etPhone.text.toString()
        val street = binding.etStreet.text.toString()
        val city = binding.etCity.text.toString()
        val postCode = binding.etPostCode.text.toString()
        val contactPhoto = binding.ivContactPicture.drawable.toBitmap()

        if (areInputValid(firstName, lastName, phone)) {
            val address = Address(street, city, postCode)
            val contact = Contact(
                args.currentContact.id,
                firstName,
                lastName,
                address,
                phone,
                false,
                contactPhoto
            )
            contactViewModel.updateContacts(contact)
            showToast(requireContext(), "Contact has been updated!")
            val action =
                UpdateContactFragmentDirections.actionUpdateContactFragmentToAllContactsFragment()
            findNavController().navigate(action)
        } else showToast(requireContext(), "Please fill out all fields!")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}