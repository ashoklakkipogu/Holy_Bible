package com.ashok.myapplication.data.local.entity

import com.ashok.myapplication.data.local.entity.BaseModel
import com.ashok.myapplication.ui.utilities.RandomColors
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class QuotesModel: BaseModel(), Serializable{
    @SerializedName("createdDate") var createdDate: String = ""
    @SerializedName("language") var language: String = ""
    @SerializedName("quote") var quote: String = ""
    @SerializedName("author") var author: String = ""
    @SerializedName("title") var title: String = ""
    @SerializedName("image") var image: String = ""
    val color: Int = RandomColors.color
}