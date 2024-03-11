package com.ashok.bible.data.local.entry


import androidx.room.*
import com.google.gson.annotations.Expose

@Entity(tableName = "note")
public class NoteModelEntry {
    @Expose
    var createdDate: String = ""

    @Expose
    var noteName: String = ""

    @PrimaryKey(autoGenerate = true)
    @Expose
    var id: Int = 0

    @Expose
    var langauge: String = ""

    @Expose
    var bibleLangIndex: String = ""

    @Expose
    var bibleId: Int = 0
}