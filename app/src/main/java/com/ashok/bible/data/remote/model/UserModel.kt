package com.ashok.bible.data.remote.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class UserModel:BaseModel(), Serializable{
    @SerializedName("createdDate") var createdDate: String = ""
    @SerializedName("userName") var userName: String = ""
    @SerializedName("language") var language: String = ""
}