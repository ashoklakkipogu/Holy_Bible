package com.ashok.myapplication.data.local.entry

import androidx.room.*
import com.google.gson.annotations.Expose
import java.io.Serializable

@Entity(tableName = "highlights")
public class HighlightModelEntry {
    @Expose
    var book: Int = 0

    @Expose
    var chapter: Int = 0

    @Expose
    var versecount: Int = 0

    @Expose
    var verse: String = ""

    @Expose
    var bibleId: Int = 0

    @Expose
    var createdDate: String = ""

    @Expose
    var bibleIndexName: String = ""

    @Expose
    var color: String = ""

    @Expose
    var langauge: String = ""

    @PrimaryKey(autoGenerate = true)
    @Expose
    var id: Int = 0
}