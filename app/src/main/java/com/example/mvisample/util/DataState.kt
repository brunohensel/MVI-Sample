package com.example.mvisample.util

data class DataState<T>(
    var message: String? = null,
    var loading: Boolean = false,
    var data: T? = null
) {
    companion object {
        fun <T> error(message: String): DataState<T> =
            DataState(message = message, loading = false, data = null)

        fun <T> loading(isLoading: Boolean): DataState<T> =
            DataState(message = null, loading = isLoading, data = null)

        fun <T> data(message: String? = null, data: T? = null): DataState<T> =
            DataState(message = message, loading = false, data = data)
    }
}