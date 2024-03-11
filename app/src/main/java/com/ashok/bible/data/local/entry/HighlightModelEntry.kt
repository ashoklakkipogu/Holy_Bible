package com.ashok.bible.data.local.entry

import androidx.room.*
import com.google.gson.annotations.Expose

@Entity(tableName = "highlights")
public class HighlightModelEntry(

    @PrimaryKey(autoGenerate = true)
    @Expose
    var id: Int = 0,

    @Expose var createdDate: String = "",

    @Expose var langauge: String = "",

    @Expose var bibleLangIndex: String = "",

    @Expose var bibleId: Int = 0,
    @Expose var colorCode: String = ""

)