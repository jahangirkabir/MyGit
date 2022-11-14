package com.jahanbabu.mygit.login


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jahanbabu.mygit.MainDispatcherRule
import com.jahanbabu.mygit.core.extension.PreferencesDataStore
import com.jahanbabu.mygit.core.network.NetworkHandler
import com.jahanbabu.mygit.core.network.api.ApiResponseError
import com.jahanbabu.mygit.core.network.api.ApiResponseSuccess
import com.jahanbabu.mygit.features.login.data.model.AccessToken
import com.jahanbabu.mygit.features.login.repository.LoginRepository
import com.jahanbabu.mygit.features.login.view.LoginViewModel
import com.jahanbabu.mygit.features.login.view.ViewState
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class LoginViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var loginViewModel: LoginViewModel

    @MockK
    private lateinit var loginRepository: LoginRepository

    @MockK
    private lateinit var networkHandler: NetworkHandler

    @MockK
    private lateinit var preferencesDataStore: PreferencesDataStore


    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        MockKAnnotations.init(this, relaxUnitFun = true)
        mockkStatic("com.jahanbabu.mygit.core.network.NetworkHandler")
        every { networkHandler.isNetworkAvailable() } returns true
        loginViewModel = LoginViewModel(loginRepository, networkHandler, preferencesDataStore)
    }

    @Test
    fun `when logged in or session active`() = runTest {
        val truePair = Pair(true,"")
        every { networkHandler.isNetworkAvailable() } returns true
        coEvery { loginRepository.isLoggedIn() } returns truePair
        loginViewModel.checkLoginStatus()
        advanceUntilIdle()
        assertEquals(ViewState.Success, loginViewModel.viewState.value)
    }

    @Test
    fun `when not logged in or session expired`() = runTest {
        val falsePair = Pair(false,"error")
        coEvery { loginRepository.isLoggedIn() } returns falsePair
        loginViewModel.checkLoginStatus()
        advanceUntilIdle()
        assertEquals(ViewState.Error("error"), loginViewModel.viewState.value)
    }

    @Test
    fun `try to get access token from code for the first time Success`() = runTest(dispatchTimeoutMs = 2000) {
        val truePair = Pair(true,"")
        coEvery { loginRepository.isLoggedIn() } returns truePair
        val accessToken = AccessToken( "access_token","expires_in","refresh_token","refresh_token_expires_in","error","error_description")
        val response = ApiResponseSuccess.Entity(accessToken)
        coEvery { loginRepository.getAccessToken("code", true) } returns response
        loginViewModel.getAccessToken("code")
        advanceUntilIdle()
        assertEquals(ViewState.Success, loginViewModel.viewState.value)
    }

    @Test
    fun `access token saved on Success`() = runTest(dispatchTimeoutMs = 2000) {
        val truePair = Pair(true,"")
        coEvery { loginRepository.isLoggedIn() } returns truePair
        val accessToken = AccessToken( "access_token","expires_in","refresh_token","refresh_token_expires_in","error","error_description")
        val response = ApiResponseSuccess.Entity(accessToken)
        coEvery { loginRepository.getAccessToken("code", true) } returns response
        loginViewModel.getAccessToken("code")
        advanceUntilIdle()
        coVerify { preferencesDataStore.setAccessToken("access_token") }
    }


    @Test
    fun `try to get access token from code for the first time Error`() = runTest(dispatchTimeoutMs = 2000) {
        val truePair = Pair(true,"")
        coEvery { loginRepository.isLoggedIn() } returns truePair
        val response = ApiResponseError.NotFound
        coEvery { loginRepository.getAccessToken("code", true) } returns response
        loginViewModel.getAccessToken("code")
        advanceUntilIdle()
        assertEquals(ViewState.Error("error"), loginViewModel.viewState.value)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
    }
}