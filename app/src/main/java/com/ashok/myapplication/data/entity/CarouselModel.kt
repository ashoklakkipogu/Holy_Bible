package com.ashok.myapplication.data.entity

import com.ashok.myapplication.data.entity.BaseModel
import com.google.gson.annotations.SerializedName

class CarouselModel : BaseModel(){
    @SerializedName("mediaUrl") var mediaUrl: String = ""
    @SerializedName("mediaType") var mediaType: String = ""
    @SerializedName("userId") var userId:String = ""
    @SerializedName("userName") var userName:String = ""
    @SerializedName("description") var description:String = ""
    @SerializedName("title") var title:String = ""
}