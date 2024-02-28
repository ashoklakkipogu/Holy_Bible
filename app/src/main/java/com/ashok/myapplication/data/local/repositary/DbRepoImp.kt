package com.ashok.myapplication.data.local.repositary

import androidx.lifecycle.LiveData
import com.ashok.myapplication.data.entity.FavBookMark
import com.ashok.myapplication.data.local.dao.*
import com.ashok.myapplication.data.local.entry.BibleIndexModelEntry
import com.ashok.myapplication.data.local.entry.BibleModelEntry
import com.ashok.myapplication.data.local.entry.FavoriteModelEntry
import com.ashok.myapplication.data.local.entry.HighlightModelEntry
import com.ashok.myapplication.data.local.entry.NoteModelEntry
import kotlinx.coroutines.Deferred
import org.intellij.lang.annotations.Language
import javax.inject.Inject

class DbRepoImp @Inject constructor(
    private val bibleDao: BibleDao,
    private val bibleIndexDao: BibleIndexDao,
    private val favoriteDao: FavoriteDao,
    private val noteDao: NoteDao,
    private val highlightDao: HighlightDao,
    private val lyricsDao: LyricsDao
) : DbRepository {
    override fun getBible() = bibleDao.getAllBibleData()
    override suspend fun getBibleScrollPosition(
        bookId: Int,
        chapterId: Int,
        verseId: Int
    ) = bibleDao.getBibleScrollPosition(
        bookId,
        chapterId,
        verseId
    )

    override suspend fun getBibleScrollLastPosition(
        bookId: Int
    ) = bibleDao.getBibleScrollLastPosition(
        bookId
    )

    override suspend fun insertBible(bible: List<BibleModelEntry>) =
        bibleDao.insertBibleContent(bible)

    override suspend fun insertBibleIndex(bible: ArrayList<BibleIndexModelEntry>) =
        bibleIndexDao.insertBibleIndex(bible)

    override suspend fun getBibleListByBookIdAndChapterId(
        bookId: Int,
        chapterId: Int,
        language: String
    ) = bibleDao.getBibleListByBookIdAndChapterId(
        bookId,
        chapterId,
        language
    )

    override suspend fun getBibleIndex(language: String) = bibleIndexDao.getAllBibleIndexContent(language)

    override suspend fun getBibleChaptersByBookIdAndLangauge(id: Int, language: String) =
        bibleDao.getBibleChaptersByBookIdAndLangauge(id, language)

    override suspend fun getBibleVerseByBookIdAndChapterId(
        bookId: Int,
        chapterID: Int,
        language: String
    ) =
        bibleDao.getBibleVerseByBookIdAndChapterId(bookId, chapterID, language)

    override suspend fun getBibleByBookIdAndChapterIdAndVerse(
        bookId: Int,
        chapterID: Int,
        verseId: Int
    ) = bibleDao.getAllBibleContentByBookIdAndChapterIdAndVerse(
        bookId,
        chapterID,
        verseId
    )

    override suspend fun getAllFav() = favoriteDao.getAllFavorites()


    override suspend fun deleteFavoriteByBibleLangIndex(bibleLangIndex: String) =
        favoriteDao.deleteFavoriteByBibleLangIndex(bibleLangIndex)

    override suspend fun getAllHighlights() = highlightDao.getAllHighlight()
    override suspend fun getAllNotes() = noteDao.getAllNote()
    override suspend fun getAllNoteList() = noteDao.getAllNoteList()
    override suspend fun getNotesById(id: Int) = noteDao.getNotesById(id)
    override suspend fun insertAllFav(favList: ArrayList<FavoriteModelEntry>) =
        favoriteDao.insertFavorites(favList)

    override suspend fun insertAllNotes(noteList: ArrayList<NoteModelEntry>) =
        noteDao.insertNote(noteList)

    override suspend fun insertAllHighlight(highlights: ArrayList<HighlightModelEntry>) =
        highlightDao.insertHighlight(highlights)

    override suspend fun getHighlightById(id: Int) = highlightDao.getHighlightById(id)
    override suspend fun deleteHighlight(id: Int) = highlightDao.deleteHighlightById(id)
    override suspend fun deleteHighlightByBibleLangIndex(bibleLangIndex: String) =
        highlightDao.deleteHighlightByBibleLangIndex(bibleLangIndex)

    override suspend fun deleteNotesByBibleLangIndex(bibleLangIndex: String) =
        noteDao.deleteNotesByBibleLangIndex(bibleLangIndex)

    override suspend fun deleteNote(id: Int) = noteDao.deleteNoteById(id)
    override suspend fun deleteFavorite(id: Int) = favoriteDao.deleteFavoriteById(id)
    override suspend fun getAllLyrics(id: Int) = lyricsDao.getAllLyricsList()
}