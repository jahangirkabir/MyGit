package com.jahanbabu.mygit.features.users.view

import android.graphics.Color
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.jahanbabu.mygit.R
import com.jahanbabu.mygit.core.platform.BaseFragment
import com.jahanbabu.mygit.databinding.FragmentUsersBinding
import com.jahanbabu.mygit.features.users.model.User
import com.jahanbabu.mygit.features.users.viewModel.UsersViewModel
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UsersFragment : BaseFragment<FragmentUsersBinding>(FragmentUsersBinding::inflate) {
    override fun layoutId() = R.layout.fragment_users

    private val viewModel: UsersViewModel by viewModels()
    private lateinit var pagingAdapter: UserListAdapter

    override fun init() {
        setUpRecyclerView()
        setUpObservation()
    }

    private fun setUpRecyclerView(){
        binding.usersRecyclerView.setHasFixedSize(false)
        val llm = LinearLayoutManager(requireActivity())
        llm.orientation = LinearLayoutManager.VERTICAL
        binding.usersRecyclerView.layoutManager = llm

        binding.usersRecyclerView.addItemDecoration(
            DividerItemDecoration(
                requireActivity(),
                llm.orientation
            )
        )
        pagingAdapter = UserListAdapter(this::onUserSelect)
        binding.usersRecyclerView.adapter = pagingAdapter


    }

    private fun setUpObservation() {
        viewModel.usersViewState.observe(this, Observer { state ->
            when (state) {
                UsersViewState.Nothing -> {
                    binding.loadingIndicator.visibility = View.GONE
                    binding.loadingTextView.visibility = View.GONE
                    binding.loadingTextView.text = ""
                }
                is UsersViewState.Loading -> {
                    Log.e("UsersFragment", "Loading......")
                    binding.loadingIndicator.visibility = View.VISIBLE
                    binding.loadingTextView.visibility = View.VISIBLE
                    binding.loadingTextView.text = getString(R.string.msg_getting_users)
                    binding.usersRecyclerView.visibility = View.GONE
                }
                is UsersViewState.Success -> {
                    Log.e("UsersFragment", "Got the user list")
                    binding.loadingIndicator.visibility = View.GONE
                    binding.loadingTextView.visibility = View.GONE
                    binding.usersRecyclerView.visibility = View.VISIBLE
                    state.pagingData?.let { updateLatestData(it) }
                }
                is UsersViewState.Error -> {
                    Log.e("UsersFragment", "Error: ${state.errorMessage}")
                    // Go back to login screen and try again
                    goBackToLoginScreen()
                }
            }
        })
    }

    private fun updateLatestData(pagingData: PagingData<User>) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                pagingAdapter.submitData(pagingData)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        requireActivity().finish()
    }

    private fun goBackToLoginScreen() {
        navigateToId(R.id.nav_action_users_to_login)
    }

    private fun onUserSelect(userName: String) {
        // go to next screen
        val action = UsersFragmentDirections.navActionUsersToUser(userName)
        findNavController().navigate(action)
    }
}
