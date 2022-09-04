package com.sopian.mygithub.di

import com.sopian.mygithub.core.domain.usecase.UsersInteractor
import com.sopian.mygithub.core.domain.usecase.UsersUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {

    @Binds
    @ViewModelScoped
    abstract fun provideUsersUseCase(usersInteractor: UsersInteractor): UsersUseCase

}