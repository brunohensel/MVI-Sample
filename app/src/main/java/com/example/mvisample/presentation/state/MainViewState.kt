package com.example.mvisample.presentation.state

import com.example.mvisample.model.BlogPost
import com.example.mvisample.model.User

data class MainViewState(
    var blogPosts: List<BlogPost>? = null,
    var user: User? = null
)