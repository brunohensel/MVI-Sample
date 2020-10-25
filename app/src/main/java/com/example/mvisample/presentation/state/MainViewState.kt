package com.example.mvisample.presentation.state

import com.example.mvisample.model.BLogPost
import com.example.mvisample.model.User

data class MainViewState(
    val blogPosts: List<BLogPost>,
    val user: User
)