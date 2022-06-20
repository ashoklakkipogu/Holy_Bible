package com.ashok.bible.data.local.entry

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import java.io.Serializable

@Entity(tableName = "note")
public class NoteModelEntry {
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
    var noteName: String = ""

    @Expose
    var bibleIndexName: String = ""

    @PrimaryKey(autoGenerate = true)
    @Expose
    var id: Int = 0
}