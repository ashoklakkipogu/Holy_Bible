package com.ashok.myapplication.data.local.model

import com.google.gson.annotations.Expose

data class HighlightModel(
    var Book: Int = 0,

    var Chapter: Int = 0,

    var Versecount: Int = 0,

    var verse: String = "",

    var langauge: String = "",

    var bibleId: Int = 0,

    var bibleLangIndex: String = "",

    var bibleIndex: String = "", var createdDate: String = "",
    var colorCode: String = "",
    var id: Int = 0

)