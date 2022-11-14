package com.jahanbabu.mygit.features.userDetails.viewModel

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jahanbabu.mygit.core.network.api.ApiResponse
import com.jahanbabu.mygit.core.network.api.ApiResponseError
import com.jahanbabu.mygit.core.network.api.ApiResponseSuccess
import com.jahanbabu.mygit.features.userDetails.repository.UserDetailsRepository
import com.jahanbabu.mygit.features.userDetails.view.UserViewState
import com.jahanbabu.mygit.features.users.model.User
import com.jahanbabu.mygit.features.users.view.UsersViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersDetailsViewModel @Inject constructor(
    private val userDetailsRepository: UserDetailsRepository
): ViewModel() {

    private val _userViewState = MutableLiveData<UserViewState>()
    val userViewState: LiveData<UserViewState>
        get() = _userViewState

    init {
        _userViewState.value = UserViewState.Loading
    }

    fun getUserDetails(username: String) {
        when(_userViewState.value){
            is UserViewState.Success -> {
                Log.e("getUserDetails", "No need to call, we have previous value")
            }
            else -> {
                viewModelScope.launch {
                    processResponse(userDetailsRepository.getUserDetails(username))
                }
            }
        }
    }

    private fun processResponse(response: ApiResponse<User>): User? =
        when (response) {
            is ApiResponseSuccess.Entity -> {
                _userViewState.value = UserViewState.Success(false,"", response.entity)
                response.entity
            }
            is ApiResponseError -> {
                _userViewState.value = UserViewState.Error("error")
                null
            }
        }
}