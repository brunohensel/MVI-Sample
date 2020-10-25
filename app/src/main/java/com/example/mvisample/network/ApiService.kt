package com.example.mvisample.network

import androidx.lifecycle.LiveData
import com.example.mvisample.model.BLogPost
import com.example.mvisample.model.User
import com.example.mvisample.util.GenericApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("placeholder/blogs")
    fun fetchBlogPosts(): LiveData<GenericApiResponse<List<BLogPost>>>

    @GET("placeholder/user/{userId}")
    fun fetchUser(@Path("userId") userId: String): LiveData<GenericApiResponse<User>>
}