package com.example.mvisample.domain

import androidx.lifecycle.LiveData
import com.example.mvisample.model.BLogPost
import com.example.mvisample.model.User
import com.example.mvisample.network.RetrofitBuilder
import com.example.mvisample.presentation.state.MainViewState
import com.example.mvisample.util.ApiSuccessResponse
import com.example.mvisample.util.DataState
import com.example.mvisample.util.GenericApiResponse

object Repository {

    fun fetchBlogPosts(): LiveData<DataState<MainViewState>> =
        object : NetworkBoundResource<List<BLogPost>, MainViewState>() {

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<List<BLogPost>>) {
                result.value = DataState.data(data = MainViewState(blogPosts = response.body))
            }

            override fun createCall(): LiveData<GenericApiResponse<List<BLogPost>>> {
                return RetrofitBuilder.apiService.fetchBlogPosts()
            }
        }.asLiveData()

    fun fetchUser(userId: String): LiveData<DataState<MainViewState>> =
        object : NetworkBoundResource<User, MainViewState>() {
            override fun handleApiSuccessResponse(response: ApiSuccessResponse<User>) {
                result.value = DataState.data(data = MainViewState(user = response.body))
            }

            override fun createCall(): LiveData<GenericApiResponse<User>> {
                return RetrofitBuilder.apiService.fetchUser(userId)
            }
        }.asLiveData()
}