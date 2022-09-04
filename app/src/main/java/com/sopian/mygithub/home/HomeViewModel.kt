package com.sopian.mygithub.home

import androidx.lifecycle.*
import com.sopian.imageapp.core.utils.Event
import com.sopian.mygithub.core.domain.usecase.UsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: UsersUseCase,
    state: SavedStateHandle
) : ViewModel() {
    private val currentQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)

    val users = currentQuery.switchMap {
        useCase.getSearchUsers(it).asLiveData()
    }

    fun searchUsers(query: String) {
        currentQuery.value = query
    }

    private val _navigateToDetail = MutableLiveData<Event<String>>()
    val navigateToDetail: LiveData<Event<String>>
        get() = _navigateToDetail

    fun onUserClicked(login: String) {
        _navigateToDetail.value = Event(login)
    }

    companion object {
        private const val CURRENT_QUERY = "current_query"
        private const val DEFAULT_QUERY = "ahmadso120"
    }
}