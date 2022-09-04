package com.sopian.mygithub.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sopian.mygithub.R
import com.sopian.mygithub.core.data.source.Resource
import com.sopian.mygithub.core.domain.model.User
import com.sopian.mygithub.core.utils.loadPhotoUrl
import com.sopian.mygithub.core.utils.showSnackbar
import com.sopian.mygithub.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {
    private val binding: FragmentDetailBinding by viewBinding()

    private val args: DetailFragmentArgs by navArgs()

    private val viewModel: DetailViewModel by viewModels()

    private lateinit var repositoriesAdapter: RepositoriesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val login = args.login
        Log.d("DetailFragment", login)
        viewModel.setLogin(login)

        binding.toolbar.setOnClickListener {
            findNavController().navigateUp()
        }

        setupAdapter()
        observeUi()
    }

    private fun observeUi() {
        if (activity != null) {
            viewModel.repositories.observe(viewLifecycleOwner) { repositories ->
                when(repositories) {
                    is Resource.Error -> {
//                        showErrorState()
                        Log.e("DetailFragment", repositories.message.toString())
                        view?.showSnackbar(getString(R.string.error_state_title))
                    }
                    is Resource.Loading -> {
//                        showLoadingState()
                    }
                    is Resource.Success -> {
//                        showSuccessState()
                        if (repositories.data != null) {
                            repositoriesAdapter.submitList(repositories.data)
                        }
                    }
                }
            }

            viewModel.user.observe(viewLifecycleOwner) { user ->
                when(user) {
                    is Resource.Error -> {
//                        showErrorState()
                        Log.e("DetailFragment", user.message.toString())
                        view?.showSnackbar(getString(R.string.error_state_title))
                    }
                    is Resource.Loading -> {
//                        showLoadingState()
                    }
                    is Resource.Success -> {
//                        showSuccessState()
                        if (user.data != null) {
                            bindViewDetail(user.data)
                        }
                    }
                }
            }

        }
    }

    private fun bindViewDetail(data: User) {
        binding.apply {
            userImage.loadPhotoUrl(data.avatarUrl)
            loginTv.text = data.login
            nameTv.text = data.name
            bioTv.text = data.bio
        }
    }

    private fun setupAdapter() {
        repositoriesAdapter = RepositoriesAdapter()
        val lm = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.apply {
            layoutManager = lm
            adapter = repositoriesAdapter
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    lm.orientation
                )
            )
        }
    }

//    private fun showSuccessState() {
//        binding.contentLoadingLayout.isVisible = false
//        binding.contentLoadingLayout.hide()
//    }
//
//    private fun showErrorState() {
//        binding.contentLoadingLayout.isVisible = false
//        binding.contentLoadingLayout.hide()
//    }
//
//    private fun showLoadingState() {
//        binding.contentLoadingLayout.isVisible = true
//        binding.contentLoadingLayout.show()
//    }
}