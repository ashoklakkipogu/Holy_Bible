package com.ashok.bible.data.local.entry

import androidx.room.*
import com.google.gson.annotations.Expose

@Entity(tableName = "favorite")
public class FavoriteModelEntry(

    @Expose var createdDate: String = "",

    @Expose var langauge: String = "",

    @Expose var bibleLangIndex: String = "",

    @PrimaryKey(autoGenerate = true) @Expose var id: Int = 0,

    @Expose var bibleId: Int = 0
)