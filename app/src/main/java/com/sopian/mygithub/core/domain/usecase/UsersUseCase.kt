package com.sopian.mygithub.core.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.sopian.mygithub.core.data.source.Resource
import com.sopian.mygithub.core.data.source.remote.response.UserRepositoriesResponse
import com.sopian.mygithub.core.domain.model.SearchUsers
import com.sopian.mygithub.core.domain.model.User
import com.sopian.mygithub.core.domain.model.UserRepositories
import kotlinx.coroutines.flow.Flow

interface UsersUseCase {
    fun getSearchUsers(query: String): Flow<Resource<out List<SearchUsers>>>
    fun getUserRepositories(login: String): Flow<Resource<out List<UserRepositories>>>
    fun getUser(login: String): Flow<Resource<out User>>
}