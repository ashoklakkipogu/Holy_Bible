package com.ashok.bible.data.repository

import android.content.SharedPreferences
import com.ashok.bible.data.error.DataError
import com.ashok.bible.data.local.dao.*
import com.ashok.bible.data.local.entry.BibleIndexModelEntry
import com.ashok.bible.data.local.entry.BibleModelEntry
import com.ashok.bible.data.local.entry.FavoriteModelEntry
import com.ashok.bible.data.local.entry.HighlightModelEntry
import com.ashok.bible.data.local.entry.NoteModelEntry
import com.ashok.bible.data.local.model.FavModel
import com.ashok.bible.data.local.model.HighlightModel
import com.ashok.bible.data.local.model.NoteModel
import com.ashok.bible.domain.RequestState
import com.ashok.bible.domain.repository.DbRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import com.ashok.bible.ui.utilities.SharedPrefUtils

class DbRepoImp @Inject constructor(
    private val bibleDao: BibleDao,
    private val bibleIndexDao: BibleIndexDao,
    private val favoriteDao: FavoriteDao,
    private val noteDao: NoteDao,
    private val highlightDao: HighlightDao,
    private val lyricsDao: LyricsDao,
    val pref: SharedPreferences
) : DbRepository {
    //override fun getBible() = bibleDao.getAllBibleData()

    override suspend fun getBibleScrollPosition(
        bookId: Int,
        chapterId: Int,
        verseId: Int
    ): Flow<RequestState<BibleModelEntry>> = wrap {
        val language = SharedPrefUtils.getLanguage(pref)!!
        bibleDao.getBibleScrollPosition(
            bookId,
            chapterId,
            verseId,
            language
        )
    }

    override suspend fun getBiblePageActionLeftOrRight(
        clickAction: String,
        bookId: Int,
        chapterId: Int
    ): Flow<RequestState<List<BibleModelEntry>>> = wrap {
        val language = SharedPrefUtils.getLanguage(pref)!!
        var data = bibleDao.getBibleListByBookIdAndChapterId(bookId, chapterId, language)
        if (data.isNullOrEmpty()) {
            if (clickAction == "LEFT" && bookId != 1 && chapterId != 1) {
                val lastPos = bibleDao.getBibleScrollLastPosition(bookId - 1)
                if (lastPos != null)
                    data = bibleDao.getBibleListByBookIdAndChapterId(
                        lastPos.Book,
                        lastPos.Chapter,
                        language
                    )!!
            } else if (clickAction == "RIGHT" && bookId != 66 && chapterId != 22) {
                data = bibleDao.getBibleListByBookIdAndChapterId(bookId + 1, 1, language)!!
            }
        }
        data

    }

    override suspend fun getAllNotes(): Flow<RequestState<List<NoteModel>>> =
        wrap {
            val language = SharedPrefUtils.getLanguage(pref)!!
            noteDao.getAllNote(language)?.sortedBy { sort ->
                sort.createdDate
            }?.reversed()
        }

    override suspend fun getAllFav(): Flow<RequestState<List<FavModel>>> =
        wrap {
            val language = SharedPrefUtils.getLanguage(pref)!!
            favoriteDao.getAllFavorites(language)?.sortedBy { sort -> sort.createdDate }?.reversed()
        }


    override suspend fun getBibleIndex(): Flow<RequestState<List<BibleIndexModelEntry>>> = wrap {
        val language = SharedPrefUtils.getLanguage(pref)!!
        bibleIndexDao.getAllBibleIndexContent(language)
    }

    override suspend fun getLanguage(language: String): Flow<RequestState<List<BibleIndexModelEntry>>> =
        wrap {
            bibleIndexDao.getLanguage(language)
        }

    override suspend fun getAllHighlights(): Flow<RequestState<List<HighlightModel>>> =
        wrap {
            val language = SharedPrefUtils.getLanguage(pref)!!
            highlightDao.getAllHighlight(language)?.sortedBy { sort -> sort.createdDate }
                ?.reversed()
        }

    override suspend fun getBibleVerseByBookIdAndChapterId(
        bookId: Int,
        chapterID: Int
    ): Flow<RequestState<List<BibleModelEntry>>> = wrap {
        val language = SharedPrefUtils.getLanguage(pref)!!
        bibleDao.getBibleVerseByBookIdAndChapterId(bookId, chapterID, language)
    }


    override suspend fun deleteHighlightByBibleLangIndex(bibleLangIndex: String) {
        highlightDao.deleteHighlightByBibleLangIndex(bibleLangIndex)
    }

    override suspend fun insertAllHighlight(highlights: ArrayList<HighlightModelEntry>) =
        highlightDao.insertHighlight(highlights)

    override suspend fun deleteFavoriteByBibleLangIndex(bibleLangIndex: String) =
        favoriteDao.deleteFavoriteByBibleLangIndex(bibleLangIndex)

    override suspend fun insertAllFav(favList: ArrayList<FavoriteModelEntry>) =
        favoriteDao.insertFavorites(favList)

    override suspend fun deleteNotesByBibleLangIndex(bibleLangIndex: String) =
        noteDao.deleteNotesByBibleLangIndex(bibleLangIndex)

    override suspend fun insertAllNotes(noteList: ArrayList<NoteModelEntry>) =
        noteDao.insertNote(noteList)

    override suspend fun deleteNote(id: Int) = noteDao.deleteNoteById(id)

    override suspend fun deleteHighlight(id: Int) = highlightDao.deleteHighlightById(id)

    override suspend fun deleteFavorite(id: Int) = favoriteDao.deleteFavoriteById(id)

    override suspend fun insertBibleIndex(bible: ArrayList<BibleIndexModelEntry>) =
        bibleIndexDao.insertBibleIndex(bible)

    override suspend fun insertBible(bible: List<BibleModelEntry>) =
        bibleDao.insertBibleContent(bible)


    private fun <T : Any> wrap(function: suspend () -> T?): Flow<RequestState<T>> {
        return flow {
            emit(RequestState.Loading)
            val response = function()
            try {
                if (response != null) {
                    emit(RequestState.Success(data = response))
                } else {
                    emit(RequestState.Error(DataError.Local.EMPTY_RESPONSE))
                }

            } catch (e: Exception) {
                emit(RequestState.Error(DataError.Local.EMPTY_RESPONSE))
            }
        }
    }
}