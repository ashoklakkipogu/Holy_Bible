package com.ashok.myapplication.ui.utilities

import com.ashok.myapplication.data.model.ApiError

sealed class Result<T>(val data: T? = null, val apiError: ApiError? = null) {
    class Loading<T>(val isLoading: Boolean = true) : Result<T>(null)
    class Success<T>(data: T?) : Result<T>(data)
    class Error<T>(apiError: ApiError, data: T? = null) : Result<T>(data, apiError)
}