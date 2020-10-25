package com.example.mvisample.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.mvisample.network.RetrofitBuilder
import com.example.mvisample.presentation.state.MainViewState
import com.example.mvisample.util.ApiEmptyResponse
import com.example.mvisample.util.ApiErrorResponse
import com.example.mvisample.util.ApiSuccessResponse
import com.example.mvisample.util.DataState

object Repository {

    fun fetchBlogPosts(): LiveData<DataState<MainViewState>> =
        Transformations
            .switchMap(RetrofitBuilder.apiService.fetchBlogPosts()) { apiResponse ->
                object : LiveData<DataState<MainViewState>>() {
                    override fun onActive() {
                        super.onActive()
                        value = when (apiResponse) {
                            is ApiSuccessResponse -> {
                                DataState.data(data = MainViewState(blogPosts = apiResponse.body))
                            }
                            is ApiErrorResponse -> {
                                DataState.error(message = apiResponse.errorMessage)
                            }
                            is ApiEmptyResponse -> {
                                DataState.error(message = "HTTP 204 - Returned NOTHING")
                            }
                        }
                    }
                }
            }

    fun fetchUser(userId: String): LiveData<DataState<MainViewState>> =
        Transformations
            .switchMap(RetrofitBuilder.apiService.fetchUser(userId)) { apiResponse ->
                object : LiveData<DataState<MainViewState>>() {
                    override fun onActive() {
                        super.onActive()
                        value = when (apiResponse) {
                            is ApiSuccessResponse -> {
                                DataState.data(data = MainViewState(user = apiResponse.body))
                            }
                            is ApiErrorResponse -> {
                                DataState.error(message = apiResponse.errorMessage)
                            }
                            is ApiEmptyResponse -> {
                                DataState.error(message = "HTTP 204 - Returned NOTHING")
                            }
                        }
                    }
                }
            }
}