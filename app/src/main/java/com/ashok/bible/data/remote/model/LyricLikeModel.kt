package com.ashok.bible.data.remote.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class LyricLikeModel: Serializable {
    @SerializedName("createdData") var createdData:String = ""
    @SerializedName("userID") var userID:String = ""
}