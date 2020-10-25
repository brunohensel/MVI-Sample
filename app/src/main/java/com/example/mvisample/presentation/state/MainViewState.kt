package com.example.mvisample.presentation.state

import com.example.mvisample.model.BLogPost
import com.example.mvisample.model.User

data class MainViewState(
    var blogPosts: List<BLogPost>? = null,
    var user: User? = null
)