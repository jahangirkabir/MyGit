package com.jahanbabu.mygit.features.login.view

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jahanbabu.mygit.core.extension.Constants.TAG
import com.jahanbabu.mygit.core.extension.PreferencesDataStore
import com.jahanbabu.mygit.core.network.NetworkHandler
import com.jahanbabu.mygit.core.network.api.ApiResponse
import com.jahanbabu.mygit.core.network.api.ApiResponseError
import com.jahanbabu.mygit.core.network.api.ApiResponseSuccess
import com.jahanbabu.mygit.features.login.data.model.AccessToken
import com.jahanbabu.mygit.features.login.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val networkHandler: NetworkHandler,
    private val preferencesDataStore: PreferencesDataStore
): ViewModel() {

    val viewState = MutableLiveData<ViewState>()

    init {
        viewState.value = ViewState.Loading
        checkLoginStatus()
    }

    fun checkLoginStatus() {
        if (networkHandler.isNetworkAvailable()){
            viewModelScope.launch {
                val isLoggedIn = loginRepository.isLoggedIn()
                if (isLoggedIn.first){
                    // proceed to users screen
                    viewState.value = ViewState.Success
                } else{
                    // hide loading & Try to Login
                    viewState.value = ViewState.Nothing
                    viewState.value = ViewState.Error(isLoggedIn.second)
                }
            }
        } else{
            viewState.value = ViewState.Error("No Internet, Please connect to the Internet.")
        }
    }

    fun getAccessToken(code: String) {
        viewModelScope.launch {
            try {
                // Get access token
                val accessToken = processResponse(loginRepository.getAccessToken(code, true))
                Log.e(TAG, "AccessToken: ${accessToken!!.accessToken}")

                accessToken.accessToken?.let {
                    val time = System.currentTimeMillis()
                    Log.e(TAG, "AccessToken & time: $it - $time")
                    preferencesDataStore.setAccessToken(it)
                    preferencesDataStore.setAccessTokenCreateTime(time)

                }
                accessToken.refreshToken?.let {
                    preferencesDataStore.setRefreshedToken(it)
                }
                viewState.value = ViewState.Success
            } catch (e: Exception) {
                Log.d(TAG, "getAccessToken: error $e")
            }
        }
    }


    private fun processResponse(response: ApiResponse<AccessToken>): AccessToken? =
        when (response) {
            is ApiResponseSuccess.Entity -> {
                response.entity
            }
            is ApiResponseError -> {
                viewState.value = ViewState.Error("error")
                null
            }
        }
}