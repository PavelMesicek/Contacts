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
import com.example.contacts.R
import com.example.contacts.databinding.FragmentNewContactBinding
import com.example.contacts.model.Address
import com.example.contacts.model.Contact
import com.example.contacts.presentation.ui.InfoDialog.Companion.showToast
import com.example.contacts.utils.HelperUtils.Companion.areInputValid
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

        binding.ivContactPicture.setImageResource(R.drawable.contact_default_image)

        val getImage = registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) {
            binding.ivContactPicture.setImageURI(it)
        }

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
        val phone = binding.etPhone.text.toString()
        val street = binding.etStreet.text.toString()
        val city = binding.etCity.text.toString()
        val postCode = binding.etPostCode.text.toString()
        val contactPhoto = binding.ivContactPicture.drawable.toBitmap()

        if (areInputValid(firstName, lastName, phone)) {
            val address = Address(street, city, postCode)
            val contact =
                Contact(
                    id = 0,
                    firstName = firstName,
                    lastName = lastName,
                    address = address,
                    phone = phone,
                    isFavorit = false,
                    contactPhoto = contactPhoto
                )
            contactViewModel.insertContacts(contact)
            showToast(requireContext(),"Successfully added!")
            val action = NewContactFragmentDirections
                .actionNewContactFragmentToAllContactsFragment()
            findNavController().navigate(action)
        } else showToast(requireContext(),"Please fill out all fields!")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}