package com.ashok.bible.data.remote.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class LyricsPostModel: BaseModel(), Serializable{
    @SerializedName("title") var title:String = ""
    @SerializedName("userID") var userID:String = ""
    @SerializedName("userName") var userName:String = ""
    @SerializedName("profileImage") var profileImage:String = ""
    @SerializedName("desc") var desc:String = ""
    @SerializedName("image_url") var imageUrl:String = ""
    @SerializedName("image_thumb") var imageThumb:String = ""
    @SerializedName("createdData") var createdData:String = ""
    @SerializedName("classID") var classID:String = ""
    var likes = emptyMap<String, LyricLikeModel>()
    var comments = emptyMap<String, LyricCommentsModel>()

    var pos:Int = 0
        get() = field
        set(value) {
            field = value
        }
}