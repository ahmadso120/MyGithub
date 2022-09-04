package com.sopian.mygithub.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sopian.mygithub.core.data.source.local.entities.SearchUsersEntity

@Database(entities = [SearchUsersEntity::class], version = 1, exportSchema = false)
abstract class MyGithubDatabase : RoomDatabase() {
    abstract fun myGithubDao(): MyGithubDao
}