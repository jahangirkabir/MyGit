package com.jahanbabu.mygit.features.login.view

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.jahanbabu.mygit.BuildConfig
import com.jahanbabu.mygit.R
import com.jahanbabu.mygit.core.platform.BaseFragment
import com.jahanbabu.mygit.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    override fun layoutId() = R.layout.fragment_login
    private val viewModel: LoginViewModel by viewModels()

    private fun navigateToRepoList() {
        navigateToId(R.id.nav_action_login_to_users)
    }


    override fun onResume() {
        super.onResume()
        val gitUri: Uri? = requireActivity().intent?.data
        if (gitUri != null){
            val code = gitUri.getQueryParameter("code")
            if(code != null){
                viewModel.getAccessToken(code)
            } else if((gitUri.getQueryParameter("error")) != null){
                Log.e("TAG", "error: ${gitUri.getQueryParameter("error")}")
            }
        }
    }

    override fun init() {
        binding.btnLogin.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW, Uri.parse("${BuildConfig.oauthLoginURL}?client_id=${BuildConfig.clientID}&scope=repo"))
            startActivity(intent)
        }

        setUpObservation()
    }

    private fun setUpObservation() {
        viewModel.viewState.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                ViewState.Nothing -> {
                    binding.loadingIndicator.visibility = View.GONE
                    binding.loadingTextView.visibility = View.GONE
                    binding.loadingTextView.text = ""
                    binding.btnLogin.isEnabled = true
                }
                is ViewState.Loading -> {
                    binding.loadingIndicator.visibility = View.VISIBLE
                    binding.loadingTextView.visibility = View.VISIBLE
                    binding.loadingTextView.text = getString(R.string.msg_getting_access)
                    binding.btnLogin.isEnabled = false
                }
                is ViewState.Success -> {
                    notify(getString(R.string.login_success_msg))
                    navigateToRepoList()
                }
                is ViewState.Error -> {
                    if(state.errorMessage.isNotEmpty()) {
                        notify(state.errorMessage)
                        binding.loadingTextView.visibility = View.VISIBLE
                        binding.loadingTextView.text = state.errorMessage
                    }
                }
            }
        })
    }
}
