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
        bibleDao.getBibleScrollPosition(
            bookId,
            chapterId,
            verseId
        )
    }

    override suspend fun getBiblePageActionLeftOrRight(
        clickAction: String,
        bookId: Int,
        chapterId: Int
    ): Flow<Result<List<BibleModelEntry>>> = wrap {
        val language = SharedPrefUtils.getLanguage(pref)!!
        Log.i("bibleData", "bibleData......l" + language)
        var data = bibleDao.getBibleListByBookIdAndChapterId(bookId, chapterId, language)
        Log.i("bibleData", "bibleData......d" + data?.size)


        if (data.isNullOrEmpty()) {
            if (clickAction == "LEFT" && bookId != 1 && chapterId != 1) {
                val lastPos = bibleDao.getBibleScrollLastPosition(bookId - 1)
                if (lastPos != null)
                    data =bibleDao.getBibleListByBookIdAndChapterId(
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
        wrap { noteDao.getAllNote()?.sortedBy { sort -> sort.createdDate }?.reversed() }

    override suspend fun getAllFav(): Flow<Result<List<FavModel>>> =
        wrap { favoriteDao.getAllFavorites()?.sortedBy { sort -> sort.createdDate }?.reversed() }


    override suspend fun getBibleIndex(): Flow<Result<List<BibleIndexModelEntry>>> = wrap {
        val language = SharedPrefUtils.getLanguage(pref)!!
        bibleIndexDao.getAllBibleIndexContent(language)
    }

    override suspend fun getAllHighlights(): Flow<Result<List<HighlightModel>>> =
        wrap { highlightDao.getAllHighlight()?.sortedBy { sort -> sort.createdDate }?.reversed() }

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
                if (response != null)
                    emit(
                        Result.Success(
                            data = response
                        )
                    )
            } catch (e: Exception) {
                emit(
                    Result.Error(
                        message = e.message.toString()
                    )
                )
            }
        }
    }

}