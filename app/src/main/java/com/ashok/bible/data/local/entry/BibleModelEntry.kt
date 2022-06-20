package com.ashok.bible.data.local.entry

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import java.io.Serializable

@Entity(tableName = "bible")
public class BibleModelEntry {
    @Expose
    var Book: Int = 0
    @Expose
    var Chapter: Int = 0
    @Expose
    var Versecount: Int = 0
    @Expose
    var verse: String = ""

    @PrimaryKey(autoGenerate = true)
    @Expose
    var id: Int = 0
}