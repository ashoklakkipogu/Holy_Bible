package com.ashok.bible.data.model

open class Errors (
    var error: Error? = null

)

open class Error (
    var code: Int = 0,
    var message: String = ""
)
