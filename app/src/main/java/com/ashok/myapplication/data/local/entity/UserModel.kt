package com.ashok.myapplication.data.local.entity

import com.ashok.myapplication.data.local.entity.BaseModel
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class UserModel: BaseModel(), Serializable{
    @SerializedName("createdDate") var createdDate: String = ""
    @SerializedName("userName") var userName: String = ""
    @SerializedName("language") var language: String = ""
}