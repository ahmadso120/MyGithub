package com.sopian.mygithub.core.data.source.remote.network

import com.sopian.mygithub.core.data.source.remote.response.ListSearchUsersResponse
import com.sopian.mygithub.core.data.source.remote.response.UserRepositoriesResponse
import com.sopian.mygithub.core.data.source.remote.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    suspend fun getSearchUsers(@Query("q") q: String): ListSearchUsersResponse

    @GET("users/{login}")
    suspend fun getUser(@Path("login") login: String): UserResponse

    @GET("users/{login}/repos")
    suspend fun getRepositories(
        @Path("login") login: String,
        @Query("sort") sort: String = "updated"
    ): List<UserRepositoriesResponse>
}