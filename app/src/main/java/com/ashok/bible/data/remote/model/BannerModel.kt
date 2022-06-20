package com.ashok.bible.data.remote.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class BannerModel:BaseModel(){
    @SerializedName("image") var image: String = ""
}