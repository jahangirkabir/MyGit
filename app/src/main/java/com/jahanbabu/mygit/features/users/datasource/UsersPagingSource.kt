package com.jahanbabu.mygit.features.users.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jahanbabu.mygit.core.network.api.ApiResponse
import com.jahanbabu.mygit.core.network.api.ApiResponseError
import com.jahanbabu.mygit.core.network.api.ApiResponseSuccess
import com.jahanbabu.mygit.core.network.api.RetrofitApiManager
import com.jahanbabu.mygit.core.extension.PreferencesDataStore
import com.jahanbabu.mygit.features.users.model.User
import com.jahanbabu.mygit.features.users.api.UsersListService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UsersPagingSource @Inject constructor(
    private val preferencesDataStore: PreferencesDataStore,
    private var apiManager: RetrofitApiManager,
    private var service: UsersListService) : PagingSource<Int, User>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val token = preferencesDataStore.getAccessToken().getOrDefault("")
        var pageIndex = params.key ?: 1
        return try {
            val request = service.getUsers(token, pageIndex, 40)
            val usersResponse = processResponse(apiManager.call(request))
            val nextKey =
                if (usersResponse == null || usersResponse.isEmpty()) {
                    null
                } else {
                    // By default, initial load size = 3 * NETWORK PAGE SIZE
                    // ensure we're not requesting duplicating items at the 2nd request
                    pageIndex+40
                }
            if (usersResponse != null){
                LoadResult.Page(
                    data = usersResponse,
                    prevKey = if (pageIndex == 1) null else pageIndex,
                    nextKey = nextKey
                )
            } else{
                return LoadResult.Error(IllegalStateException(""))
            }
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    /**
     * The refresh key is used for subsequent calls to PagingSource.Load after the initial load.
     */
    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index.
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    private fun processResponse(response: ApiResponse<List<User>>): List<User>? =
        when (response) {
            is ApiResponseSuccess.Entity -> response.entity
            is ApiResponseError -> {
                null
            }
        }
}