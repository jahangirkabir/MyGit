package com.jahanbabu.mygit.features.users.view

import androidx.paging.PagingData
import com.jahanbabu.mygit.features.users.model.User

sealed class UsersViewState {
    object Nothing : UsersViewState()
    object Loading : UsersViewState()

    data class Success(
        val isError: Boolean,
        val message: String,
        val pagingData: PagingData<User>?
    ) : UsersViewState()
    data class Error(val errorMessage: String) : UsersViewState()
}