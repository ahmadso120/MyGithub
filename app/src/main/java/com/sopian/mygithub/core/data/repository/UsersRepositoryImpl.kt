package com.sopian.mygithub.core.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.sopian.mygithub.core.DataMapper
import com.sopian.mygithub.core.data.source.remote.network.NetworkBoundResource
import com.sopian.mygithub.core.data.source.Resource
import com.sopian.mygithub.core.data.source.local.datasource.UsersLocalDataSource
import com.sopian.mygithub.core.data.source.remote.datasource.UsersRemoteDataSource
import com.sopian.mygithub.core.data.source.remote.network.ApiResponse
import com.sopian.mygithub.core.data.source.remote.network.NetworkService
import com.sopian.mygithub.core.data.source.remote.response.SearchUsersResponse
import com.sopian.mygithub.core.data.source.remote.response.UserRepositoriesResponse
import com.sopian.mygithub.core.data.source.remote.response.UserResponse
import com.sopian.mygithub.core.domain.model.SearchUsers
import com.sopian.mygithub.core.domain.model.User
import com.sopian.mygithub.core.domain.model.UserRepositories
import com.sopian.mygithub.core.utils.HasInternetCapability
import com.sopian.mygithub.core.utils.RateLimiter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersRepositoryImpl @Inject constructor(
    private val remoteDataSource: UsersRemoteDataSource,
    private val localDataSource: UsersLocalDataSource,
    private val hasInternetCapability: HasInternetCapability
) : UsersRepository {

    private val rateLimiter = RateLimiter<String>(1, TimeUnit.MINUTES)

    override fun getSearchUsers(query: String): Flow<Resource<out List<SearchUsers>>> =
        object : NetworkBoundResource<List<SearchUsers>, List<SearchUsersResponse>>() {
            override fun loadFromDB(): Flow<List<SearchUsers>> {
                return localDataSource.getSearchUsers(query).map {
                    DataMapper.mapSearchUsersEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<SearchUsers>?): Boolean{
                if(!hasInternetCapability.isConnected) return false

                return data == null || data.isEmpty() ||
                        rateLimiter.shouldFetch(LIST_SEARCH_USERS)
            }

            override suspend fun createCall(): Flow<ApiResponse<List<SearchUsersResponse>>> =
                remoteDataSource.getSearchUsers(query)

            override suspend fun saveCallResult(data: List<SearchUsersResponse>) {
                localDataSource.deleteAllSearchUsers()
                data.map {
                    it.query = query
                }
                localDataSource.insertSearchUsers(
                    DataMapper.mapSearchUsersResponsesToEntities(data)
                )
            }

            override fun onFetchFailed() {
                rateLimiter.reset(LIST_SEARCH_USERS)
            }
        }.asFlow()

    override fun getUserRepositories(login: String): Flow<Resource<out List<UserRepositories>>> =
        object: NetworkService<List<UserRepositories>, List<UserRepositoriesResponse>>() {
            override fun load(data: List<UserRepositoriesResponse>?): Flow<List<UserRepositories>> {
                return flow {
                    if (data != null) {
                        emit(DataMapper.mapUserRepositoriesResponsesToDomain(data))
                    }
                }
            }

            override suspend fun createCall(): Flow<ApiResponse<List<UserRepositoriesResponse>>> =
                remoteDataSource.getUserRepositories(login)

        }.asFlow()

    override fun getUser(login: String): Flow<Resource<out User>> =
        object: NetworkService<User, UserResponse>() {
            override fun load(data: UserResponse?): Flow<User> {
                return flow {
                    DataMapper.mapUserResponsesToDomain(data)?.let {
                        emit(it)
                    }
                }
            }

            override suspend fun createCall(): Flow<ApiResponse<UserResponse>> =
                remoteDataSource.getUser(login)

        }.asFlow()

    companion object {
        private const val LIST_SEARCH_USERS = "LIST_SEARCH_USERS"
    }
}