package com.sopian.mygithub.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sopian.mygithub.core.data.source.local.entities.SearchUsersEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MyGithubDao {
    @Query("SELECT * FROM search_users WHERE `query` = :query")
    fun getSearchUsers(query: String? = null): Flow<List<SearchUsersEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSearchUsers(users: List<SearchUsersEntity>)

    @Query("DELETE FROM search_users")
    suspend fun deleteAllSearchUsers()
}