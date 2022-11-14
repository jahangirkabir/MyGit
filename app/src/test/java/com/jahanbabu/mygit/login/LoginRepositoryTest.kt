package com.jahanbabu.mygit.login

import com.jahanbabu.mygit.core.extension.PreferencesDataStore
import com.jahanbabu.mygit.core.network.api.ApiResponseError
import com.jahanbabu.mygit.core.network.api.ApiResponseSuccess
import com.jahanbabu.mygit.core.network.api.RetrofitApiManager
import com.jahanbabu.mygit.features.login.api.UserLoginService
import com.jahanbabu.mygit.features.login.data.model.AccessToken
import com.jahanbabu.mygit.features.login.datasource.LoginDataSource
import com.jahanbabu.mygit.features.login.repository.LoginRepository
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class LoginRepositoryTest {

    private lateinit var loginRepository: LoginRepository

    private lateinit var loginDataSource: LoginDataSource

    @MockK
    private lateinit var apiManager: RetrofitApiManager

    @MockK
    private lateinit var preferencesDataStore: PreferencesDataStore

    @MockK
    private lateinit var service: UserLoginService

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
//        apiManager = RetrofitApiManager(retrofit = RetrofitApiManager)
        loginDataSource = LoginDataSource(apiManager, service)
        loginRepository = LoginRepository(loginDataSource, preferencesDataStore)
    }

    @Test
    fun `check login state`() = runTest {
        val accessToken = AccessToken( "access_token","expires_in","refresh_token","refresh_token_expires_in","error","error_description")
        val response = ApiResponseSuccess.Entity(accessToken)
        coEvery { loginDataSource.getRefreshToken("token") } returns response
        coEvery { preferencesDataStore.getAccessToken().getOrDefault("") } returns "token"
        coEvery { preferencesDataStore.getRefreshedToken().getOrDefault("") } returns "token"
        coEvery { preferencesDataStore.getTokenCreateTime().getOrDefault(0) } returns 0
//        val expectedEntity = response.entity
        val actualEntity = (loginRepository.isLoggedIn().first)
        advanceUntilIdle()
        Assert.assertEquals(true, actualEntity)
    }

    @Test
    fun `check login state within session time`() = runTest {
        val accessToken = AccessToken( "access_token","expires_in","refresh_token","refresh_token_expires_in","error","error_description")
        val response = ApiResponseSuccess.Entity(accessToken)
        coEvery { loginDataSource.getRefreshToken("token") } returns response
        coEvery { preferencesDataStore.getAccessToken().getOrDefault("") } returns "token"
        coEvery { preferencesDataStore.getRefreshedToken().getOrDefault("") } returns "token"
        coEvery { preferencesDataStore.getTokenCreateTime().getOrDefault(0) } returns System.currentTimeMillis()
        val actualEntity = (loginRepository.isLoggedIn().first)
        advanceUntilIdle()
        Assert.assertEquals(true, actualEntity)
    }

    @Test
    fun `check login state with response error`() = runTest {
        val response = ApiResponseError.NotFound
        coEvery { loginDataSource.getRefreshToken("token") } returns response
        coEvery { preferencesDataStore.getAccessToken().getOrDefault("") } returns "token"
        coEvery { preferencesDataStore.getRefreshedToken().getOrDefault("") } returns "token"
        coEvery { preferencesDataStore.getTokenCreateTime().getOrDefault(0) } returns 0
//        val expectedEntity = response.entity
        val actualEntity = (loginRepository.isLoggedIn().first)
        advanceUntilIdle()
        Assert.assertEquals(false, actualEntity)
    }

    @Test
    fun `check login state for first start`() = runTest {
        coEvery { preferencesDataStore.getAccessToken().getOrDefault("") } returns ""
        val actualEntity = (loginRepository.isLoggedIn().first)
        advanceUntilIdle()
        Assert.assertEquals(false, actualEntity)
    }

    @Test
    fun `get access token first time`() = runTest {
        val accessToken = AccessToken( "access_token","expires_in","refresh_token","refresh_token_expires_in","error","error_description")
        val response = ApiResponseSuccess.Entity(accessToken)
        coEvery { loginDataSource.getAccessToken("code") } returns response
        val expectedEntity = response.entity
        val actualEntity = (loginRepository.getAccessToken("code", true) as ApiResponseSuccess.Entity).entity
        Assert.assertEquals(expectedEntity.accessToken, actualEntity.accessToken)
    }

    @Test
    fun `get access token second time`() = runTest {
        val accessToken = AccessToken( "access_token","expires_in","refresh_token","refresh_token_expires_in","error","error_description")
        val response = ApiResponseSuccess.Entity(accessToken)
        coEvery { loginDataSource.getRefreshToken("code") } returns response
        val expectedEntity = response.entity
        val actualEntity = (loginRepository.getAccessToken("code", false) as ApiResponseSuccess.Entity).entity
        Assert.assertEquals(expectedEntity.accessToken, actualEntity.accessToken)
    }
}
