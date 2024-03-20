package com.ashok.bible.ui.common

import com.ashok.bible.data.AppConstants.NO_DATA_FOUND
import com.ashok.bible.data.model.DataError

fun DataError.dataErrorUiText():String{
    return when(this){
        DataError.Local.DISK_FULL -> {
            ""
        }
        DataError.Network.NO_CONNECTION -> "No internet connection. Please connect to a working network."
        DataError.Network.BAD_RESPONSE -> "error in getting response "
        DataError.Network.TIMEOUT -> "Time out  error"
        DataError.Network.EMPTY_RESPONSE -> NO_DATA_FOUND
        DataError.Network.NOT_DEFINED -> "an unexpected error"
        DataError.Network.UNKNOWN -> "Something went wrong"
    }
}