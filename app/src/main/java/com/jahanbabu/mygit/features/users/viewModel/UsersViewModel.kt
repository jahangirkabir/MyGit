package com.jahanbabu.mygit.features.users.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.jahanbabu.mygit.core.network.api.ApiResponse
import com.jahanbabu.mygit.core.network.api.ApiResponseError
import com.jahanbabu.mygit.core.network.api.ApiResponseSuccess
import com.jahanbabu.mygit.features.users.model.User
import com.jahanbabu.mygit.features.users.repository.UserListRepository
import com.jahanbabu.mygit.features.users.view.UsersViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val userListRepository: UserListRepository
): ViewModel() {

    private val _usersViewState = MutableLiveData<UsersViewState>()
    val usersViewState: LiveData<UsersViewState>
        get() = _usersViewState

    init {
        _usersViewState.value = UsersViewState.Loading
        viewModelScope.launch {
            getUserList()
        }
    }

    private suspend fun getUserList(){
        userListRepository.getUserList().cachedIn(viewModelScope).collectLatest {
            _usersViewState.value = UsersViewState.Success(false, "", it)
        }
    }
}