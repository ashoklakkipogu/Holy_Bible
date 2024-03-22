package com.ashok.bible.data.error

sealed interface DataError: Error {
    enum class Network: DataError {
        NO_CONNECTION,
        BAD_RESPONSE,
        TIMEOUT,
        EMPTY_RESPONSE,
        NOT_DEFINED,
        UNKNOWN
    }
    enum class Local: DataError {
        DISK_FULL,
        EMPTY_RESPONSE
    }
}