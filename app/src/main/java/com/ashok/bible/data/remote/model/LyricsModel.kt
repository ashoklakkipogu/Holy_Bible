package com.ashok.bible.data.remote.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "lyrics")
class LyricsModel: Serializable {
    @PrimaryKey(autoGenerate = true)
    @Expose
    var index: Int = 0

    @Expose
    var lyricId: String = ""

    @Expose
    @SerializedName("title")
    var title: String = ""

    @Expose
    @SerializedName("desc")
    var desc: String = ""

    @Expose
    @SerializedName("youtubeId")
    var youtubeId: String = ""

    @Expose
    @SerializedName("youtubeTrackId")
    var youtubeTrackId: String = ""

    @Expose
    @SerializedName("language")
    var language: String = ""

    @Expose
    @SerializedName("content")
    var content: String = ""

    @Expose
    @SerializedName("createdDate")
    var createdDate: String = ""

    @Expose
    @SerializedName("singers")
    var singers: String = ""

    @Expose
    @SerializedName("content_en")
    var contentEn: String = ""

    @Expose
    @SerializedName("title_en")
    var titleEn: String = ""
    var isSecondLan: Boolean = false
    var isProgressBar: Boolean = false
}