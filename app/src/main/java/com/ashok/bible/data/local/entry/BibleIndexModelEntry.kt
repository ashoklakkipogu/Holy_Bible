package com.ashok.bible.data.local.entry

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import java.io.Serializable

@Entity(tableName = "bible_index")
public class BibleIndexModelEntry {
    @Expose
    var chapter: String = ""
    @Expose
    var chapter_id: Int = 0
    @PrimaryKey(autoGenerate = true)
    @Expose
    var id: Int = 0
}