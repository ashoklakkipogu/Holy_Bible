package com.ashok.myapplication.data.local.entity

import com.ashok.myapplication.data.local.entity.BaseModel
import com.ashok.myapplication.ui.utilities.RandomColors
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class StatusEmptyImagesModel: BaseModel(), Serializable{
    @SerializedName("createdDate") var createdDate: String = ""
    @SerializedName("image") var image: String = ""
}