package com.ashok.bible.ui.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class LyricsDataTrans:Serializable {
    @SerializedName("title") var title: String = ""
    @SerializedName("content") var content: String = ""
}