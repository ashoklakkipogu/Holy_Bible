package com.ashok.myapplication.data.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class BannerModel:BaseModel(){
    @SerializedName("image") var image: String = ""
}