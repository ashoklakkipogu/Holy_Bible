package com.ashok.bible.data.remote.model

import java.io.Serializable

open class BaseModel(
    var id: String = "",
    var name: String = "",
    var succss: Int = 0
): Serializable