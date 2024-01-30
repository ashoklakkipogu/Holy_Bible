package com.ashok.myapplication.domain

import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose

data class BibleModel(
    var Book: Int = 0,

    var Chapter: Int = 0,

    var Versecount: Int = 0,

    var verse: String = "",

    var langauge: String = "",

    var bibleID: String = "",

    var bibleLangIndex: String = "",

    var bibleIndex: String = "",
    var id: Int = 0,

    //var isSelected: Boolean = false,
    var selectedBackground: String = "",
    var isNote: Boolean = false,
    var isBookMark: Boolean = false
){

}