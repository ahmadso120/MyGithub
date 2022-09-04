package com.sopian.mygithub.core.data.source.remote.datasource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.sopian.mygithub.core.data.source.remote.network.ApiResponse
import com.sopian.mygithub.core.data.source.remote.network.ApiService
import com.sopian.mygithub.core.data.source.remote.response.SearchUsersResponse
import com.sopian.mygithub.core.data.source.remote.response.UserRepositoriesResponse
import com.sopian.mygithub.core.data.source.remote.response.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersRemoteDataSource @Inject constructor(private val apiService: ApiService) {
    suspend fun getSearchUsers(query: String): Flow<ApiResponse<List<SearchUsersResponse>>> {
        return flow {
            try {
                val response = apiService.getSearchUsers(query)

                val dataArray = response.items
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.items))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

//    fun getUserRepositories(login: String): LiveData<PagingData<UserRepositoriesResponse>> {
//        Log.d("UsersRemoteDataSource", login)
//        return Pager(
//            config = PagingConfig(
//                pageSize = 10,
//                enablePlaceholders = false
//            ),
//            pagingSourceFactory = {
//                UserRepositoriesPagingSource(apiService, login)
//            }
//        ).liveData
//    }

    fun getUser(login: String) : Flow<ApiResponse<UserResponse>> {
        return flow {
            try {
                val response = apiService.getUser(login)
                Log.d("UsersRemoteDataSource", response.toString())
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("UsersRemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getUserRepositories(login: String) : Flow<ApiResponse<List<UserRepositoriesResponse>>> {
        return flow {
            try {
                val response = apiService.getRepositories(login)
                Log.d("UsersRemoteDataSource", response.toString())
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("UsersRemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}