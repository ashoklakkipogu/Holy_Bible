package com.ashok.bible.domain.repository

import com.ashok.bible.data.local.entry.BibleIndexModelEntry
import com.ashok.bible.data.local.entry.BibleModelEntry
import com.ashok.bible.data.local.entry.FavoriteModelEntry
import com.ashok.bible.data.local.entry.HighlightModelEntry
import com.ashok.bible.data.local.entry.NoteModelEntry
import com.ashok.bible.data.local.model.FavModel
import com.ashok.bible.data.local.model.HighlightModel
import com.ashok.bible.data.local.model.NoteModel
import kotlinx.coroutines.flow.Flow
import com.ashok.bible.ui.utilities.Result

interface DbRepository {
    //fun getBible(): PagingSource<Int, BibleModelEntry>
    suspend fun getBibleScrollPosition(
        bookId: Int,
        chapterId: Int,
        verseId: Int
    ): Flow<Result<BibleModelEntry>>

    suspend fun getBiblePageActionLeftOrRight(
        clickAction: String = "",
        bookId: Int = 1,
        chapterId: Int = 1,
    ): Flow<Result<List<BibleModelEntry>>>

    suspend fun getBibleIndex(): Flow<Result<List<BibleIndexModelEntry>>>
    suspend fun getLanguage(language: String): Flow<Result<List<BibleIndexModelEntry>>>


    suspend fun deleteHighlightByBibleLangIndex(bibleLangIndex: String)
    suspend fun insertAllHighlight(highlights: ArrayList<HighlightModelEntry>)

    suspend fun deleteFavoriteByBibleLangIndex(bibleLangIndex: String)
    suspend fun insertAllFav(favList: ArrayList<FavoriteModelEntry>)
    suspend fun deleteNotesByBibleLangIndex(bibleLangIndex: String)
    suspend fun insertAllNotes(noteList: ArrayList<NoteModelEntry>)
    suspend fun getAllNotes(): Flow<Result<List<NoteModel>>>

    suspend fun getAllFav(): Flow<Result<List<FavModel>>>

    suspend fun getAllHighlights(): Flow<Result<List<HighlightModel>>>
    suspend fun getBibleVerseByBookIdAndChapterId(
        bookId: Int,
        chapterID: Int
    ): Flow<Result<List<BibleModelEntry>>>


    suspend fun deleteNote(id: Int)

    suspend fun deleteHighlight(id: Int)

    suspend fun deleteFavorite(id: Int)

    suspend fun insertBibleIndex(bible: ArrayList<BibleIndexModelEntry>): List<Long>

    suspend fun insertBible(bible: List<BibleModelEntry>): List<Long>

    /*suspend fun getBibleScrollLastPosition(bible: Int): BibleModelEntry?
    suspend fun getBibleListByBookIdAndChapterId(
        bookId: Int,
        chapterId: Int,
        language: String
    ): List<BibleModelEntry>?

    suspend fun getBibleIndex(language:String): List<BibleIndexModelEntry>
    suspend fun getBibleChaptersByBookIdAndLangauge(id: Int, language: String): List<BibleModelEntry>


    suspend fun getBibleByBookIdAndChapterIdAndVerse(
        bookId: Int,
        chapterID: Int,
        verseId: Int
    ): LiveData<List<BibleModelEntry>>

    suspend fun getAllFav(): List<FavModel>?

    suspend fun getAllNotes(): List<NoteModel>?
    suspend fun getAllNoteList(): List<NoteModelEntry>
    suspend fun getNotesById(id: Int): LiveData<NoteModelEntry>


    suspend fun getHighlightById(id: Int): LiveData<HighlightModelEntry>
    suspend fun deleteHighlightByBibleLangIndex(bibleLangIndex: String)
    suspend fun getAllLyrics(id: Int): List<LyricsModel>

    suspend fun deleteNotesByBibleLangIndex(bibleLangIndex: String)*/

}