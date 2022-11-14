package com.jahanbabu.mygit.features.login.view

sealed class ViewState {
    object Nothing : ViewState()
    object Loading : ViewState()
    object Success : ViewState()
    data class Error(val errorMessage: String) : ViewState()
}