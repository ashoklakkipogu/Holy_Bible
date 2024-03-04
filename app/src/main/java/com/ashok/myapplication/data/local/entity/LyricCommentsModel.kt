package com.ashok.myapplication.data.local.entity

import com.ashok.myapplication.data.local.entity.BaseModel
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class LyricCommentsModel : BaseModel(), Serializable {
    @SerializedName("createdData") var createdData:Long = 0
    @SerializedName("userID") var userID:String = ""
    @SerializedName("message") var message:String = ""
    @SerializedName("userName") var userName:String = ""
}