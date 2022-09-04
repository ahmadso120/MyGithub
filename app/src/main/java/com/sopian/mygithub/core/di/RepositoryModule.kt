package com.sopian.mygithub.core.di

import com.sopian.mygithub.core.data.repository.UsersRepository
import com.sopian.mygithub.core.data.repository.UsersRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideUsersRepository(usersRepository: UsersRepositoryImpl): UsersRepository

}