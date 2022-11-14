package com.jahanbabu.mygit.features.userDetails.view

import com.jahanbabu.mygit.features.users.model.User


sealed class UserViewState {
    object Nothing : UserViewState()
    object Loading : UserViewState()
    data class Success(
        val isError: Boolean,
        val message: String,
        val user: User?
    ) : UserViewState()
    data class Error(val errorMessage: String) : UserViewState()
}