package com.example.mvisample.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.mvisample.util.*
import com.example.mvisample.util.Constants.Companion.TESTING_NETWORK_DELAY
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**A generic class that can provide a resource backed by the network.
 * [ResponseObject] is the object from the Retrofit response
 * [ViewStateType] is the ViewState  e.g MainViewState*/

abstract class NetworkBoundResource<ResponseObject, ViewStateType> {
    protected val result = MediatorLiveData<DataState<ViewStateType>>()

    init {
        result.value = DataState.loading(true)

        GlobalScope.launch(IO) {
            delay(TESTING_NETWORK_DELAY)//fake delay, just for test purpose
            withContext(Main) {
                val apiResponse = createCall()
                result.addSource(apiResponse) { response ->
                    result.removeSource(apiResponse)// at this point we don't need this anymore
                    handleNetworkCall(response)
                }
            }
        }
    }

    private fun handleNetworkCall(response: GenericApiResponse<ResponseObject>) {
        when (response) {
            is ApiEmptyResponse -> {
                println("DEBUG: NetworkBoundResource: HTTP 204 - Returned NOTHING")
                onReturnError("HTTP 204 - Returned NOTHING")
            }
            is ApiSuccessResponse -> {
                handleApiSuccessResponse(response)
            }
            is ApiErrorResponse -> {
                println("DEBUG: NetworkBoundResource: ${response.errorMessage}")
                onReturnError(response.errorMessage)
            }
        }
    }

    private fun onReturnError(message: String) {
        result.value = DataState.error(message = message)
    }

    fun asLiveData() = result as LiveData<DataState<ViewStateType>>

    abstract fun handleApiSuccessResponse(response: ApiSuccessResponse<ResponseObject>)
    abstract fun createCall(): LiveData<GenericApiResponse<ResponseObject>>
}