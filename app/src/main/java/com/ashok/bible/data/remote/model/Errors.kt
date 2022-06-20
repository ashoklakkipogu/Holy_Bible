package com.ashok.bible.data.remote.model

open class Errors (
    var error: Error? = null

)

open class Error (
    var code: Int = 0,
    var message: String = ""
)
