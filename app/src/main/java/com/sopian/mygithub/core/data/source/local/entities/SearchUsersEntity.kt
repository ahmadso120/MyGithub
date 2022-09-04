package com.sopian.mygithub.core.data.source.local.entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_users")
data class SearchUsersEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo("id")
    val id: Int,

    @ColumnInfo("avatarUrl")
    val avatarUrl: String,

    @ColumnInfo("login")
    val login: String,

    @ColumnInfo(name = "query")
    val query: String?,
)
