package com.sopian.mygithub.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sopian.imageapp.core.utils.EventObserver
import com.sopian.mygithub.R
import com.sopian.mygithub.core.data.source.Resource
import com.sopian.mygithub.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding: FragmentHomeBinding by viewBinding()

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var userAdapter: UserAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                if (query != null) {
                    binding.recyclerView.scrollToPosition(0)
                    viewModel.searchUsers(query)
                    binding.search.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        setupAdapter()
        observeUi()
    }

    private fun setupAdapter() {
        userAdapter = UserAdapter {
            viewModel.onUserClicked(it.login)
        }
        val lm = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.apply {
            layoutManager = lm
            adapter = userAdapter
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    lm.orientation
                )
            )
        }
    }

    private fun observeUi() {
        if (activity != null) {
            viewModel.users.observe(viewLifecycleOwner) { searchUsers ->
                if (searchUsers != null) {
                    when(searchUsers) {
                        is Resource.Error -> {
                            showErrorState()
                            Log.e("error_home", searchUsers.message.toString())
                        }
                        is Resource.Loading -> {
                            showLoadingState()
                        }
                        is Resource.Success -> {
                            if (searchUsers.data != null) {
                                showSuccessState()
                                userAdapter.submitList(searchUsers.data)
                                Log.d("data_home", searchUsers.data.toString())
                            } else {
                                showEmptyState()
                            }
                        }
                    }
                }
            }

            viewModel.navigateToDetail.observe(viewLifecycleOwner, EventObserver {
                val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(it)
                findNavController().navigate(action)
            })
        }
    }

    private fun showSuccessState() {
        binding.errorStateLayout.root.isVisible = false
        binding.emptyStateLayout.root.isVisible = false

        binding.contentLoadingLayout.isVisible = false
        binding.contentLoadingLayout.hide()
    }

    private fun showEmptyState() {
        binding.errorStateLayout.root.isVisible = false
        binding.emptyStateLayout.root.isVisible = true

        binding.emptyStateLayout.emptyErrorStateTitle.text =
            getString(R.string.empty_state_title)
        binding.emptyStateLayout.emptyErrorStateSubtitle.text =
            getString(R.string.no_search_results_subtitle)
    }

    private fun showErrorState() {
        binding.contentLoadingLayout.isVisible = false
        binding.contentLoadingLayout.hide()

        binding.errorStateLayout.root.isVisible = true
        binding.emptyStateLayout.root.isVisible = false

        binding.errorStateLayout.emptyErrorStateTitle.text =
            getString(R.string.error_state_title)
    }

    private fun showLoadingState() {
        binding.errorStateLayout.root.isVisible = false
        binding.emptyStateLayout.root.isVisible = false

        binding.contentLoadingLayout.isVisible = true
        binding.contentLoadingLayout.show()
    }
}