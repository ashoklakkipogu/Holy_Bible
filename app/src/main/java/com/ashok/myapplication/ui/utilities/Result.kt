package com.ashok.myapplication.ui.utilities

sealed class Result<T> {
    class Loading<T> : Result<T>()
    data class Success<T>(val data: T) : Result<T>()
    data class Error<T>(val error: Any) : Result<T>()
}
