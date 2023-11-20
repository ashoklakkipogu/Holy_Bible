package com.ashok.myapplication.data.entity

data class FavBookMark(
    var book: Int = 0,
    var chapter: Int = 0,
    var versecount: Int = 0,
    var verse: String = "",
    var bibleId: Int = 0,
    var createdDate: String = "",
    var bibleIndexName: String = "",
    var color: String = "",
    var favId: Int = 0,
    var highlightId: Int = 0,
    var isFav: Boolean = true,
    var noData: Boolean = false
)
