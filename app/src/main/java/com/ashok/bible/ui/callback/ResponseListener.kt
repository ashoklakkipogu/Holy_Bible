package com.ashok.bible.ui.callback

interface ResponseListener<T> {
    fun getResult(data: T)
    fun onErrorResponse(t: Throwable?)
}