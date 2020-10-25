package com.example.mvisample.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.mvisample.network.RetrofitBuilder
import com.example.mvisample.presentation.state.MainViewState
import com.example.mvisample.util.ApiEmptyResponse
import com.example.mvisample.util.ApiErrorResponse
import com.example.mvisample.util.ApiSuccessResponse

object Repository {

    fun fetchBlogPosts() =
        Transformations
            .switchMap(RetrofitBuilder.apiService.fetchBlogPosts()) { apiResponse ->
                object : LiveData<MainViewState>() {
                    override fun onActive() {
                        super.onActive()
                        value = when (apiResponse) {
                            is ApiSuccessResponse -> {
                                MainViewState(blogPosts = apiResponse.body)
                            }
                            is ApiErrorResponse -> {
                                MainViewState()
                            }
                            is ApiEmptyResponse -> {
                                MainViewState()
                            }
                        }
                    }
                }
            }

    fun fetchUser(userId: String) =
        Transformations
            .switchMap(RetrofitBuilder.apiService.fetchUser(userId)) { apiResponse ->
                object : LiveData<MainViewState>() {
                    override fun onActive() {
                        super.onActive()
                        value = when (apiResponse) {
                            is ApiSuccessResponse -> {
                                MainViewState(user = apiResponse.body)
                            }
                            is ApiErrorResponse -> {
                                MainViewState()
                            }
                            is ApiEmptyResponse -> {
                                MainViewState()
                            }
                        }
                    }
                }
            }
}