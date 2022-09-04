package com.sopian.mygithub.core

import com.sopian.mygithub.core.data.source.local.entities.SearchUsersEntity
import com.sopian.mygithub.core.data.source.remote.response.SearchUsersResponse
import com.sopian.mygithub.core.data.source.remote.response.UserRepositoriesResponse
import com.sopian.mygithub.core.data.source.remote.response.UserResponse
import com.sopian.mygithub.core.domain.model.SearchUsers
import com.sopian.mygithub.core.domain.model.User
import com.sopian.mygithub.core.domain.model.UserRepositories

object DataMapper {
    fun mapSearchUsersResponsesToEntities(
        input: List<SearchUsersResponse>
    ): List<SearchUsersEntity> {
        val searchUsersList = ArrayList<SearchUsersEntity>()

        input.map {
            val searchUsers = SearchUsersEntity(
                it.id,
                it.avatarUrl,
                it.login,
                it.query
            )
            searchUsersList.add(searchUsers)
        }
        return searchUsersList
    }

    fun mapSearchUsersEntitiesToDomain(
        input: List<SearchUsersEntity>
    ): List<SearchUsers> = input.map {
        SearchUsers(
            it.id,
            it.avatarUrl,
            it.login
        )
    }

    fun mapUserRepositoriesResponsesToDomain(
        input: List<UserRepositoriesResponse>
    ): List<UserRepositories> = input.map {
        UserRepositories(
            it.id,
            it.name,
            it.description,
            it.updatedAt,
            it.stargazersCount
        )
    }

    fun mapUserResponsesToDomain(input: UserResponse?): User? {
        var user: User? = null
        input?.let {
            user = User(
                it.login,
                it.id,
                it.avatarUrl,
                it.name,
                it.bio
            )
        }

        return user
    }
}