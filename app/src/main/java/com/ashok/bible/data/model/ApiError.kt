package com.ashok.bible.data.model

import com.ashok.bible.data.AppConstants.NO_DATA_FOUND


data class ApiError(
    val status: ApiStatus,
    val code: Int = -1,
    var message: String? = "",
    var error: Errors? = null
) {
    enum class ApiStatus {
        NO_CONNECTION,
        BAD_RESPONSE,
        TIMEOUT,
        EMPTY_RESPONSE,
        NOT_DEFINED
    }

}