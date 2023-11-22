package com.ashok.myapplication.data.local.entry

import androidx.room.*
import com.google.gson.annotations.Expose
import java.io.Serializable

@Entity(tableName = "bible", indices = [Index(value = ["bibleID"], unique = true)])
data class BibleModelEntry(
    @Expose var Book: Int = 0,

    @Expose var Chapter: Int = 0,

    @Expose var Versecount: Int = 0,

    @Expose var verse: String = "",

    @Expose var langauge: String = "",

    @Expose var bibleID: String = "",

    @Expose var bibleLangIndex: String = "",

    @Expose var bibleIndex: String = "",

    @PrimaryKey(autoGenerate = true) @Expose var id: Int = 0,

    //var isSelected: Boolean = false,
    var selectedBackground: String = ""
)