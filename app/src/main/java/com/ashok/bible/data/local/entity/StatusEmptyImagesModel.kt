package com.ashok.bible.data.local.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class StatusEmptyImagesModel: BaseModel(), Serializable{
    @SerializedName("createdDate") var createdDate: String = ""
    @SerializedName("image") var image: String = ""
}