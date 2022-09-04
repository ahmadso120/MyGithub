package com.sopian.mygithub.core.data.source.remote.datasource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sopian.mygithub.core.DataMapper
import com.sopian.mygithub.core.data.source.remote.network.ApiService
import com.sopian.mygithub.core.data.source.remote.response.UserRepositoriesResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoriesPagingSource @Inject constructor(
    private val apiService: ApiService,
    private val login: String
) : PagingSource<Int, UserRepositoriesResponse>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserRepositoriesResponse> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getRepositories(login)
            Log.d("pagingSource", responseData.toString())
            LoadResult.Page(
                data = responseData,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UserRepositoriesResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}