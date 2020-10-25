package com.example.mvisample.network

import com.example.mvisample.model.BLogPost
import com.example.mvisample.model.User
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("placeholder/blogs")
    fun fetchUser(): List<BLogPost>

    @GET("placeholder/user/{userId}")
    fun fetchUser(@Path("userId") userId: String): User
}