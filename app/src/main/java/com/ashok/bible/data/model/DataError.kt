package com.ashok.bible.data.model

sealed interface DataError {
    enum class Network: DataError {
        NO_CONNECTION,
        BAD_RESPONSE,
        TIMEOUT,
        EMPTY_RESPONSE,
        NOT_DEFINED,
        UNKNOWN
    }
    enum class Local: DataError {
        DISK_FULL
    }
}