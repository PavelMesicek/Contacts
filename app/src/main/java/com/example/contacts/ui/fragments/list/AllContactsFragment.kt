package com.example.contacts.ui.fragments.list

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contacts.R
import com.example.contacts.databinding.FragmentAllContactsBinding
import com.example.contacts.db.ContactDatabase
import com.example.contacts.repository.ContactRepository
import com.example.contacts.ui.contactList.ContactViewModel
import com.example.contacts.ui.contactList.ContactViewModelProviderFactory

class AllContactsFragment : Fragment() {
    private var _binding: FragmentAllContactsBinding? = null
    private val binding get() = _binding!!
    private lateinit var contactViewModel: ContactViewModel

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllContactsBinding.inflate(inflater, container, false)
        val contactRepository = ContactRepository(ContactDatabase(requireContext()))
        val viewModelProviderFactory = ContactViewModelProviderFactory(contactRepository)
        contactViewModel =
            ViewModelProvider(this, viewModelProviderFactory)[ContactViewModel::class.java]

        val adapter = ContactAdapter(listOf())
        binding.rvContact.layoutManager = LinearLayoutManager(requireContext())
        binding.rvContact.adapter = adapter

        contactViewModel.getAllContacts().observe(requireActivity()) { contacts ->
            adapter.items = contacts
            adapter.notifyDataSetChanged()
        }

        binding.floatingActionButton.setOnClickListener { view ->
            view.findNavController().navigate(R.id.newContactFragment)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}