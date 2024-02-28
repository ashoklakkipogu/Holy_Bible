package com.ashok.myapplication.data.local.entry

import androidx.room.*
import com.google.gson.annotations.Expose
import java.io.Serializable

@Entity(tableName = "bible_index", indices = [Index(value = ["bibleLangIndex"], unique = true)])
public class BibleIndexModelEntry {
    @Expose
    var chapter: String = ""

    @Expose
    var chapter_id: Int = 0

    @Expose
    var langauge: String = ""

    @PrimaryKey(autoGenerate = true)
    @Expose
    var id: Int = 0

    @Expose
    var bibleLangIndex: String = ""

    var isExpand: Boolean = true

    var bibleChapterCount: Int = 0
}