package com.ashok.myapplication.data.local.entry


import androidx.room.*
import com.google.gson.annotations.Expose
import java.io.Serializable

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