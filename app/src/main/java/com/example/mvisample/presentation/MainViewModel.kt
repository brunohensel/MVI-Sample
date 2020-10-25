package com.example.mvisample.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.mvisample.model.BLogPost
import com.example.mvisample.model.User
import com.example.mvisample.presentation.state.MainStateEvent
import com.example.mvisample.presentation.state.MainStateEvent.*
import com.example.mvisample.presentation.state.MainViewState
import com.example.mvisample.util.AbsentLiveData

class MainViewModel : ViewModel() {

    private val _viewState: MutableLiveData<MainViewState> = MutableLiveData()
    private val _stateEvent: MutableLiveData<MainStateEvent> = MutableLiveData()

    val viewState: LiveData<MainViewState>
        get() = _viewState

    val dataState: LiveData<MainViewState> = Transformations
        .switchMap(_stateEvent) { stateEvent ->
            handleStateEvent(stateEvent)
        }

    private fun handleStateEvent(stateEvent: MainStateEvent): LiveData<MainViewState> {
        return when (stateEvent) {
            is GetBlogPostsEvent -> {
                return object : LiveData<MainViewState>() {
                    override fun onActive() {
                        super.onActive()
                        val blogList: ArrayList<BLogPost> = ArrayList()
                        blogList.add(
                            BLogPost(
                                pk = 0,
                                title = "Vancouver PNE 2019",
                                body = "Here is Jess and I at the Vancouver PNE. We ate a lot of food.",
                                image = "https://cdn.open-api.xyz/open-api-static/static-blog-images/image8.jpg"
                            )
                        )
                        blogList.add(
                            BLogPost(
                                pk = 1,
                                title = "Ready for a Walk",
                                body = "Here I am at the park with my dogs Kiba and Maizy. Maizy is the smaller one and Kiba is the larger one.",
                                image = "https://cdn.open-api.xyz/open-api-static/static-blog-images/image2.jpg"
                            )
                        )
                        value = MainViewState(
                            blogPosts = blogList
                        )
                    }
                }
            }
            is GetUserEvent -> {
                return object : LiveData<MainViewState>() {
                    override fun onActive() {
                        super.onActive()
                        val user = User(
                            email = "mitch@tabian.c a",
                            username = "mitch",
                            image = "https://cdn.open-api.xyz/open-api-static/static-random-images/logo_1080_1080.png"
                        )
                        value = MainViewState(
                            user = user
                        )
                    }
                }
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