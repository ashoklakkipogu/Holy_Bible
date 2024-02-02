package com.ashok.myapplication.data.local.repositary

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import com.ashok.myapplication.data.entity.FavBookMark
import com.ashok.myapplication.data.entity.LyricsModel
import com.ashok.myapplication.data.local.entry.BibleIndexModelEntry
import com.ashok.myapplication.data.local.entry.BibleModelEntry
import com.ashok.myapplication.data.local.entry.FavoriteModelEntry
import com.ashok.myapplication.data.local.entry.HighlightModelEntry
import com.ashok.myapplication.data.local.entry.NoteModelEntry
import com.ashok.myapplication.data.local.model.FavModel
import com.ashok.myapplication.data.local.model.HighlightModel
import com.ashok.myapplication.data.local.model.NoteModel

interface DbRepository {
    fun getBible(): PagingSource<Int, BibleModelEntry>
    suspend fun getBibleScrollPosition(
        bookId: Int,
        chapterId: Int,
        verseId: Int
    ): BibleModelEntry?

    suspend fun getBibleScrollLastPosition(bible: Int): BibleModelEntry?
    suspend fun insertBible(bible: List<BibleModelEntry>): List<Long>
    suspend fun insertBibleIndex(bible: ArrayList<BibleIndexModelEntry>): List<Long>

    suspend fun getBibleIndex(): LiveData<List<BibleIndexModelEntry>>
    suspend fun getBibleByBookId(id: Int): LiveData<List<BibleModelEntry>>
    suspend fun getBibleByBookIdAndChapterId(
        bookId: Int,
        chapterID: Int
    ): LiveData<List<BibleModelEntry>>

    suspend fun getBibleByBookIdAndChapterIdAndVerse(
        bookId: Int,
        chapterID: Int,
        verseId: Int
    ): LiveData<List<BibleModelEntry>>

    suspend fun getAllFav(): List<FavModel>?

    suspend fun deleteFavoriteByBibleLangIndex(bibleLangIndex: String)
    suspend fun getAllHighlights(): List<HighlightModel>?
    suspend fun getAllNotes(): List<NoteModel>?
    suspend fun getAllNoteList(): List<NoteModelEntry>
    suspend fun getNotesById(id: Int): LiveData<NoteModelEntry>

    suspend fun insertAllFav(favList: ArrayList<FavoriteModelEntry>)

    suspend fun insertAllNotes(noteList: ArrayList<NoteModelEntry>)
    suspend fun insertAllHighlight(highlights: ArrayList<HighlightModelEntry>)
    suspend fun getHighlightById(id: Int): LiveData<HighlightModelEntry>
    suspend fun deleteHighlight(id: Int)
    suspend fun deleteHighlightByBibleLangIndex(bibleLangIndex: String)
    suspend fun deleteNote(id: Int)
    suspend fun deleteFavorite(id: Int)
    suspend fun getAllLyrics(id: Int): List<LyricsModel>

    suspend fun deleteNotesByBibleLangIndex(bibleLangIndex: String)

}