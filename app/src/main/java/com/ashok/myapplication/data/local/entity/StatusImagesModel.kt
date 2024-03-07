package com.ashok.myapplication.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "statusImages")
class StatusImagesModel: Serializable {
    @PrimaryKey(autoGenerate = true)
    @Expose
    var index: Int = 0

    @Expose
    @SerializedName("title")
    var title: String = ""

    @Expose
    @SerializedName("language")
    var language: String = ""

    @Expose
    @SerializedName("createdDate")
    var createdDate: String = ""

    @Expose
    @SerializedName("description")
    var description: String = ""

    @Expose
    @SerializedName("url")
    var url: String = ""

}