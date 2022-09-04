package com.sopian.mygithub.core.di

import android.content.Context
import androidx.room.Room
import com.sopian.mygithub.core.data.source.local.room.MyGithubDao
import com.sopian.mygithub.core.data.source.local.room.MyGithubDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): MyGithubDatabase = Room.databaseBuilder(
        context,
        MyGithubDatabase::class.java, "MyGithub.db"
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideMyGithubDao(database: MyGithubDatabase): MyGithubDao = database.myGithubDao()
}