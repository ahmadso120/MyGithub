package com.sopian.mygithub.detail

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sopian.mygithub.core.data.source.remote.response.UserRepositoriesResponse
import com.sopian.mygithub.core.domain.model.UserRepositories
import com.sopian.mygithub.core.domain.usecase.UsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val useCase: UsersUseCase) : ViewModel() {

    private val _setLogin = MutableLiveData<String>()
    private val _setLogin1 = MutableLiveData<String>()

    fun setLogin(login: String) {
        _setLogin.value = login
    }

    fun setLogin1(login: String) {
        _setLogin1.value = login
    }

    val user = _setLogin.switchMap {
        useCase.getUser(it).asLiveData()
    }

    val repositories = _setLogin.switchMap {
        useCase.getUserRepositories(it).asLiveData()
    }
}