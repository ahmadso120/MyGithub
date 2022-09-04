package com.sopian.mygithub.core.data.source.local.datasource

import com.sopian.mygithub.core.data.source.local.entities.SearchUsersEntity
import com.sopian.mygithub.core.data.source.local.room.MyGithubDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UsersLocalDataSource @Inject constructor(private var dao: MyGithubDao) {
    fun getSearchUsers(query: String) = dao.getSearchUsers(query)

    suspend fun insertSearchUsers(list: List<SearchUsersEntity>) = dao.insertSearchUsers(list)

    suspend fun deleteAllSearchUsers() = dao.deleteAllSearchUsers()
}