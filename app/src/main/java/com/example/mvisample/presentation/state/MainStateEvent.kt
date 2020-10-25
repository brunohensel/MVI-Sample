package com.example.mvisample.presentation.state

sealed class MainStateEvent {
    object GetBlogPostsEvent : MainStateEvent()
    data class GetUserEvent(val userId: String) : MainStateEvent()
    object Idle : MainStateEvent()
}