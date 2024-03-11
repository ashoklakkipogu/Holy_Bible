package com.ashok.bible.data.local.entity

import com.google.gson.annotations.SerializedName

class BannerModel: BaseModel(){
    @SerializedName("image") var image: String = ""
}