package com.example.mvisample.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.mvisample.domain.Repository
import com.example.mvisample.model.BLogPost
import com.example.mvisample.model.User
import com.example.mvisample.presentation.state.MainStateEvent
import com.example.mvisample.presentation.state.MainStateEvent.*
import com.example.mvisample.presentation.state.MainViewState
import com.example.mvisample.util.AbsentLiveData
import com.example.mvisample.util.DataState

class MainViewModel : ViewModel() {

    private val _viewState: MutableLiveData<MainViewState> = MutableLiveData()
    private val _stateEvent: MutableLiveData<MainStateEvent> = MutableLiveData()

    val viewState: LiveData<MainViewState>
        get() = _viewState

    val dataState: LiveData<DataState<MainViewState>> = Transformations
        .switchMap(_stateEvent) { stateEvent ->
            handleStateEvent(stateEvent)
        }

    private fun handleStateEvent(stateEvent: MainStateEvent): LiveData<DataState<MainViewState>> {
        return when (stateEvent) {
            is GetBlogPostsEvent -> {
                Repository.fetchBlogPosts()
            }
            is GetUserEvent -> {
                Repository.fetchUser(stateEvent.userId)
            }
            is Idle -> {
                AbsentLiveData.create()
            }
        }
    }

    private fun getCurrentVewStateOrNew(): MainViewState {
        return viewState.value ?: MainViewState()
    }

    fun setUserData(user: User) {
        val update = getCurrentVewStateOrNew()
        update.user = user
        _viewState.value = update
    }

    fun setBlogListData(blogPosts: List<BLogPost>) {
        val update = getCurrentVewStateOrNew()
        update.blogPosts = blogPosts
        _viewState.value = update
    }

    fun setStateEvent(event: MainStateEvent) {
        _stateEvent.value = event
    }
}