package com.jahanbabu.mygit.features.userDetails.view

import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.jahanbabu.mygit.R
import com.jahanbabu.mygit.core.platform.BaseFragment
import com.jahanbabu.mygit.databinding.FragmentUserDetailsBinding
import com.jahanbabu.mygit.features.userDetails.viewModel.UsersDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class UserDetailsFragment : BaseFragment<FragmentUserDetailsBinding>(FragmentUserDetailsBinding::inflate) {
    private val args: UserDetailsFragmentArgs by navArgs()
    override fun layoutId() = R.layout.fragment_user_details
    private val viewModel: UsersDetailsViewModel by viewModels()

    override fun init() {
        setUpToolbar()
        setUpObservation()
        getUserDetails()
    }

    private fun setUpToolbar() {
        binding.toolbar.setNavigationIcon(R.drawable.back_navigation)
        binding.toolbar.setNavigationOnClickListener {
            goBackToUserListScreen()
        }
    }

    private fun getUserDetails() {
        viewModel.getUserDetails(args.userName)
    }


    private fun setUpObservation() {
        viewModel.userViewState.observe(this) { state ->
            when (state) {
                UserViewState.Nothing -> {
                    binding.nestedScrollView.visibility = View.GONE
                    binding.progressBar.visibility = View.GONE
                    binding.loadingTextView.visibility = View.GONE
                    binding.loadingTextView.text = ""
                }
                is UserViewState.Loading -> {
                    binding.nestedScrollView.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                    binding.loadingTextView.visibility = View.VISIBLE
                    binding.loadingTextView.text = getString(R.string.msg_getting_user_details)
                }
                is UserViewState.Success -> {
                    Log.e("UsersFragment", "got the user details")
                    binding.nestedScrollView.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    binding.loadingTextView.visibility = View.GONE
                    updateUiData(state)
                }
                is UserViewState.Error -> {
                    Log.e("UsersFragment", "Error: ${state.errorMessage}")
                    // Go back to login screen and try again
                    goBackToUserListScreen()
                }
            }
        }
    }

    private fun updateUiData(state: UserViewState.Success) {
        state.user?.let { user ->
            Glide.with(binding.root.context)
                .load(user.avatarUrl)
                .circleCrop()
                .placeholder(R.drawable.github_logo)
                .into(binding.ivProfile)
            state.user.name?.let {
                binding.tvName.text = it
            }
            state.user.email?.let {
                binding.tvEmail.text = it
            }
            state.user.company?.let {
                binding.tvCompany.text = it
            }
            state.user.twitter?.let {
                binding.tvTwitter.text = it
            }
            state.user.bio?.let {
                binding.tvBio.text = it
            }
            state.user.organisation?.let {
                binding.tvOrganisation.text = it
            }
            state.user.location?.let {
                binding.tvAddress.text = it
            }
            state.user.followers?.let {
                binding.tvFollowers.text = it.toString()
            }
            state.user.following?.let {
                binding.tvFollowing.text = it.toString()
            }
        }

    }

    private fun goBackToUserListScreen() {
        onBackPressed()
    }
}
