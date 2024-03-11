package com.ashok.bible.ui.utilities

import com.ashok.bible.data.local.entry.BibleModelEntry
import com.ashok.bible.data.local.entry.FavoriteModelEntry
import com.ashok.bible.data.local.entry.HighlightModelEntry
import com.ashok.bible.data.local.entry.NoteModelEntry
import com.ashok.bible.ui.utilities.BibleUtils.getCurrentTime
import com.ashok.bible.ui.dashboard.HomeViewModel

fun highlightInsertOrDelete(bibleVerse: BibleModelEntry, viewModel: HomeViewModel){
    if (bibleVerse.selectedBackground == ""){
        highlightDelete(bibleVerse.bibleLangIndex, viewModel)
    }else{
        highlightInsert(bibleVerse, viewModel)
    }
}


fun noteInsert(title: String, bibleVerse: BibleModelEntry, viewModel: HomeViewModel) {
    val noteObj = NoteModelEntry()
    noteObj.createdDate = getCurrentTime()
    noteObj.noteName = title
    noteObj.langauge = bibleVerse.langauge
    noteObj.bibleLangIndex = bibleVerse.bibleLangIndex
    noteObj.bibleId = bibleVerse.id
    viewModel.noteInsert(noteObj)
}

fun bookmarkInsertOrDelete(bibleVerse: BibleModelEntry, viewModel: HomeViewModel) {
    if (bibleVerse.isBookMark){
        bookmarkInsert(bibleVerse, viewModel)
    }else{
        favDelete(bibleVerse.bibleLangIndex, viewModel)
    }
}
fun bookmarkInsert(bibleVerse: BibleModelEntry, viewModel: HomeViewModel) {
    val obj = FavoriteModelEntry()
    obj.createdDate = getCurrentTime()
    obj.langauge = bibleVerse.langauge
    obj.bibleLangIndex = bibleVerse.bibleLangIndex
    obj.bibleId = bibleVerse.id
    viewModel.bookmarkInsert(obj)
}

fun highlightInsert(bibleVerse: BibleModelEntry, viewModel: HomeViewModel) {
    val obj = HighlightModelEntry()
    obj.createdDate = getCurrentTime()
    obj.langauge = bibleVerse.langauge
    obj.bibleLangIndex = bibleVerse.bibleLangIndex
    obj.bibleId = bibleVerse.id
    obj.colorCode = bibleVerse.selectedBackground
    viewModel.highlightInsert(obj)
}

fun favDelete(bibleLangIndex: String, viewModel: HomeViewModel) {
    viewModel.deleteFavById(bibleLangIndex)

}

fun highlightDelete(bibleLangIndex: String, viewModel: HomeViewModel) {
    viewModel.highlightDeleteByBibleId(bibleLangIndex)
}