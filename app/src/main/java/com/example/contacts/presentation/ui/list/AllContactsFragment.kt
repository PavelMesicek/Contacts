package com.example.contacts.presentation.ui.list

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contacts.R
import com.example.contacts.databinding.FragmentAllContactsBinding
import com.example.contacts.viewmodel.ContactViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllContactsFragment : Fragment(), SearchView.OnQueryTextListener {
    private var _binding: FragmentAllContactsBinding? = null
    private val binding get() = _binding!!
    private val contactViewModel: ContactViewModel by viewModels()
    private val adapter: ContactAdapter by lazy { ContactAdapter(listOf(), contactViewModel) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllContactsBinding.inflate(inflater, container, false)

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_menu, menu)
        val search = menu.findItem(R.id.app_bar_search)
        val searchView = search?.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query != null) {
            searchContact(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if(query != null) {
            searchContact(query)
        }
        return true
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun searchContact(query: String){
        val searchQuery = "%$query%"
        contactViewModel.searchContact(searchQuery).observe(requireActivity()) { contacts ->
            contacts.let {
                contacts.also { adapter.items = it }
                adapter.notifyDataSetChanged()
            }
        }
    }
}