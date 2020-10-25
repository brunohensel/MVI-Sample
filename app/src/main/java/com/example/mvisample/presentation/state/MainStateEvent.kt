package com.example.mvisample.presentation.state

sealed class MainStateEvent {
    object GetBlogPostsEvent : MainStateEvent()
    data class GetUserEvent(val userId: Int) : MainStateEvent()
    object Idle : MainStateEvent()
}