package com.ashok.bible.data.remote.model

import com.google.gson.annotations.SerializedName

class CarouselModel : BaseModel(){
    @SerializedName("mediaUrl") var mediaUrl: String = ""
    @SerializedName("mediaType") var mediaType: String = ""
    @SerializedName("userId") var userId:String = ""
    @SerializedName("userName") var userName:String = ""
    @SerializedName("description") var description:String = ""
    @SerializedName("title") var title:String = ""
}