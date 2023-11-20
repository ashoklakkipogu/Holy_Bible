package com.ashok.myapplication.data.entity

import com.ashok.myapplication.data.entity.BaseModel
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class QuotesModel: BaseModel(), Serializable{
    @SerializedName("createdDate") var createdDate: String = ""
    @SerializedName("language") var language: String = ""
    @SerializedName("quote") var quote: String = ""
    @SerializedName("author") var author: String = ""
    @SerializedName("title") var title: String = ""
    @SerializedName("image") var image: String = ""
}