package com.example.contacts.presentation.ui.list

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contacts.R
import com.example.contacts.databinding.FragmentAllContactsBinding
import com.example.contacts.viewmodel.ContactViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllContactsFragment : Fragment() {
    private var _binding: FragmentAllContactsBinding? = null
    private val binding get() = _binding!!
    private val contactViewModel: ContactViewModel by viewModels()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllContactsBinding.inflate(inflater, container, false)

        val adapter = ContactAdapter(listOf(), contactViewModel)
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