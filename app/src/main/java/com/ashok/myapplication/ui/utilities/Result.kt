package com.ashok.myapplication.ui.utilities

sealed class Result<T>(val data: T? = null, val message: String? = null) {
    class Loading<T>(val isLoading: Boolean = true) : Result<T>(null)
    class Success<T>(data: T) : Result<T>(data)
    class Error<T>(message: String, data: T? = null) : Result<T>(data, message)
}
