package com.ashok.myapplication.data.repository

import android.content.SharedPreferences
import android.util.Log
import com.ashok.myapplication.data.local.dao.*
import com.ashok.myapplication.data.local.entry.BibleIndexModelEntry
import com.ashok.myapplication.data.local.entry.BibleModelEntry
import com.ashok.myapplication.data.local.entry.FavoriteModelEntry
import com.ashok.myapplication.data.local.entry.HighlightModelEntry
import com.ashok.myapplication.data.local.entry.NoteModelEntry
import com.ashok.myapplication.data.local.model.FavModel
import com.ashok.myapplication.data.local.model.HighlightModel
import com.ashok.myapplication.data.local.model.NoteModel
import com.ashok.myapplication.data.model.ApiError
import com.ashok.myapplication.domain.repository.DbRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import com.ashok.myapplication.ui.utilities.Result
import com.ashok.myapplication.ui.utilities.SharedPrefUtils

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
    ): Flow<Result<BibleModelEntry>> = wrap {
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
    ): Flow<Result<List<BibleModelEntry>>> = wrap {
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

    override suspend fun getAllNotes(): Flow<Result<List<NoteModel>>> =
        wrap {
            val language = SharedPrefUtils.getLanguage(pref)!!
            noteDao.getAllNote(language)?.sortedBy { sort ->
                sort.createdDate
            }?.reversed()
        }

    override suspend fun getAllFav(): Flow<Result<List<FavModel>>> =
        wrap {
            val language = SharedPrefUtils.getLanguage(pref)!!
            favoriteDao.getAllFavorites(language)?.sortedBy { sort -> sort.createdDate }?.reversed()
        }


    override suspend fun getBibleIndex(): Flow<Result<List<BibleIndexModelEntry>>> = wrap {
        val language = SharedPrefUtils.getLanguage(pref)!!
        bibleIndexDao.getAllBibleIndexContent(language)
    }

    override suspend fun getLanguage(language: String): Flow<Result<List<BibleIndexModelEntry>>> =
        wrap {
            bibleIndexDao.getLanguage(language)
        }

    override suspend fun getAllHighlights(): Flow<Result<List<HighlightModel>>> =
        wrap {
            val language = SharedPrefUtils.getLanguage(pref)!!
            highlightDao.getAllHighlight(language)?.sortedBy { sort -> sort.createdDate }
                ?.reversed()
        }

    override suspend fun getBibleVerseByBookIdAndChapterId(
        bookId: Int,
        chapterID: Int
    ): Flow<Result<List<BibleModelEntry>>> = wrap {
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
    /* override suspend fun getBibleScrollLastPosition(
         bookId: Int
     ) = bibleDao.getBibleScrollLastPosition(
         bookId
     )





     override suspend fun getBibleListByBookIdAndChapterId(
         bookId: Int,
         chapterId: Int,
         language: String
     ) = bibleDao.getBibleListByBookIdAndChapterId(
         bookId,
         chapterId,
         language
     )

     override suspend fun getBibleIndex(language: String) =
         bibleIndexDao.getAllBibleIndexContent(language)

     override suspend fun getBibleChaptersByBookIdAndLangauge(id: Int, language: String) =
         bibleDao.getBibleChaptersByBookIdAndLangauge(id, language)



     override suspend fun getBibleByBookIdAndChapterIdAndVerse(
         bookId: Int,
         chapterID: Int,
         verseId: Int
     ) = bibleDao.getAllBibleContentByBookIdAndChapterIdAndVerse(
         bookId,
         chapterID,
         verseId
     )



     override suspend fun deleteFavoriteByBibleLangIndex(bibleLangIndex: String) =
         favoriteDao.deleteFavoriteByBibleLangIndex(bibleLangIndex)

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
     override suspend fun deleteHighlightByBibleLangIndex(bibleLangIndex: String) =
         highlightDao.deleteHighlightByBibleLangIndex(bibleLangIndex)

     override suspend fun deleteNotesByBibleLangIndex(bibleLangIndex: String) =
         noteDao.deleteNotesByBibleLangIndex(bibleLangIndex)

     override suspend fun getAllLyrics(id: Int) = lyricsDao.getAllLyricsList()*/


    private fun <T : Any> wrap(function: suspend () -> T?): Flow<Result<T>> {
        return flow {
            emit(Result.Loading(true))
            val response = function()
            try {
                //if (response != null)
                /*//Log.i("response", "response.........db$response")
                //Log.i("response", "response.........db1${(response is List<*> && response.isEmpty())}")
                //Log.i("response", "response.........db2${(response is Map<*, *> && response.isEmpty())}")*/
                if (response !=null) {
                    //Log.i("response", "response.........dbs$response")
                    emit(
                        Result.Success(
                            data = response
                        )
                    )
                } else {
                    emit(Result.Error(ApiError(ApiError.ApiStatus.EMPTY_RESPONSE)))
                    //Log.i("response", "response.........dbe$response")
                }

            } catch (e: Exception) {
                emit(
                    Result.Error(
                        ApiError(ApiError.ApiStatus.EMPTY_RESPONSE, message = e.message.toString())
                    )
                )
            }
        }
    }

}