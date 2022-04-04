package com.example.contacts.presentation.ui.list

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contacts.databinding.FragmentFavoritesContactsBinding
import com.example.contacts.viewmodel.ContactViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesContactsFragment : Fragment() {

    private var _binding: FragmentFavoritesContactsBinding? = null
    private val binding get() = _binding!!
    private val contactViewModel: ContactViewModel by viewModels()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesContactsBinding.inflate(inflater, container, false)

        val adapter = ContactAdapter(listOf(), contactViewModel)
        binding.rvContact.layoutManager = LinearLayoutManager(requireContext())
        binding.rvContact.adapter = adapter

        contactViewModel.getAllFavorit().observe(requireActivity()) { contacts ->
            adapter.items = contacts
            adapter.notifyDataSetChanged()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}