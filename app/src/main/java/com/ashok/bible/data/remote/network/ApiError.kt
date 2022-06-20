package com.ashok.bible.data.remote.network

import com.ashok.bible.data.remote.model.Errors


data class ApiError(
    val status: ApiStatus,
    val code: Int = -1,
    var message: String = "",
    var error: Errors? = null
) {
    fun getErrorMessage(): String {
        message = when (status) {
            ApiStatus.EMPTY_RESPONSE -> "no data available in repository"
            ApiStatus.NO_CONNECTION -> "No internet connection. Please connect to a working network."
            ApiStatus.BAD_RESPONSE -> "error in getting response "
            ApiStatus.TIMEOUT -> " Time out  error"
            ApiStatus.NOT_DEFINED -> "an unexpected error"
        }
        /*if (message.isNullOrEmpty()) {
            message = when (status) {
                ApiStatus.EMPTY_RESPONSE -> "no data available in repository"
                ApiStatus.NO_CONNECTION -> "error in connecting to repository"
                ApiStatus.BAD_RESPONSE -> "error in getting response "
                ApiStatus.TIMEOUT -> " Time out  error"
                ApiStatus.NOT_DEFINED -> "an unexpected error"
            }
        }*/

        return message
    }

    enum class ApiStatus {
        /**
         * error in connecting to repository (Server or Database)
         */
        NO_CONNECTION,
        /**
         * error in getting response (Json Error, Server Error, etc)
         */
        BAD_RESPONSE,
        /**
         * Time out  error
         */
        TIMEOUT,
        /**
         * no data available in repository
         */
        EMPTY_RESPONSE,
        /**
         * an unexpected error
         */
        NOT_DEFINED;
    }

}