package com.ashok.myapplication.data.entity

open class Errors (
    var error: Error? = null

)

open class Error (
    var code: Int = 0,
    var message: String = ""
)
