package com.sopian.mygithub.core.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.sopian.mygithub.core.data.repository.UsersRepository
import com.sopian.mygithub.core.data.source.Resource
import com.sopian.mygithub.core.data.source.remote.response.UserRepositoriesResponse
import com.sopian.mygithub.core.domain.model.SearchUsers
import com.sopian.mygithub.core.domain.model.User
import com.sopian.mygithub.core.domain.model.UserRepositories
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UsersInteractor @Inject constructor(
    private val usersRepository: UsersRepository
) : UsersUseCase {
    override fun getSearchUsers(query: String): Flow<Resource<out List<SearchUsers>>> =
        usersRepository.getSearchUsers(query)

    override fun getUserRepositories(login: String): Flow<Resource<out List<UserRepositories>>> =
        usersRepository.getUserRepositories(login)

    override fun getUser(login: String): Flow<Resource<out User>> =
        usersRepository.getUser(login)
}