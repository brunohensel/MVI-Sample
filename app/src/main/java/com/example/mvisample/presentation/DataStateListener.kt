package com.example.mvisample.presentation

import com.example.mvisample.util.DataState

interface DataStateListener {
    fun onDataStateChange(dataState: DataState<*>?)
}