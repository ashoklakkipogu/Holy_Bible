package com.ashok.bible.ui.bibleindex


sealed class BibleIndexDetailsEvent{
    data class VerseByBookIdAndLangauge(val bookId:Int, val chapterID:Int) : BibleIndexDetailsEvent()
}