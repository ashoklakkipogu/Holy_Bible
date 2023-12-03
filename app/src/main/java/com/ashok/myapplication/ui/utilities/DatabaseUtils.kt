package com.ashok.myapplication.ui.utilities

import com.ashok.myapplication.data.local.entry.BibleModelEntry
import com.ashok.myapplication.data.local.entry.FavoriteModelEntry
import com.ashok.myapplication.data.local.entry.HighlightModelEntry
import com.ashok.myapplication.data.local.entry.NoteModelEntry
import com.ashok.myapplication.ui.utilities.BibleUtils.getCurrentTime
import com.ashok.myapplication.ui.viewmodel.HomeViewModel

fun noteInsert(title: String, bibleVerse: BibleModelEntry, viewModel: HomeViewModel) {
    val noteObj = NoteModelEntry()
    noteObj.createdDate = getCurrentTime()
    noteObj.noteName = title
    noteObj.langauge = bibleVerse.langauge
    noteObj.bibleLangIndex = bibleVerse.bibleLangIndex
    noteObj.bibleId = bibleVerse.id
    viewModel.noteInsert(noteObj)
}

fun bookmarkInsert(bibleVerse: BibleModelEntry, viewModel: HomeViewModel) {
    val obj = FavoriteModelEntry()
    obj.createdDate = getCurrentTime()
    obj.langauge = bibleVerse.langauge
    obj.bibleLangIndex = bibleVerse.bibleLangIndex
    obj.bibleId = bibleVerse.id
    viewModel.bookmarkInsert(obj)
}

fun highlightInsert(colorCode:String, bibleVerse: BibleModelEntry, viewModel: HomeViewModel) {
    val obj = HighlightModelEntry()
    obj.createdDate = getCurrentTime()
    obj.langauge = bibleVerse.langauge
    obj.bibleLangIndex = bibleVerse.bibleLangIndex
    obj.colorCode = colorCode
    obj.bibleId = bibleVerse.id
    viewModel.highlightInsert(obj)
}

fun highlightDelete(bibleVerse: BibleModelEntry, viewModel: HomeViewModel) {
    viewModel.highlightDeleteByBibleId(bibleVerse.bibleID)
}