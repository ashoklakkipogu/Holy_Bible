package com.ashok.bible.data.local.repositary

import androidx.lifecycle.LiveData
import com.ashok.bible.data.local.entry.*
import com.ashok.bible.data.remote.model.CarouselModel
import com.ashok.bible.data.remote.model.LyricsModel
import com.ashok.bible.data.remote.network.ApiError
import com.ashok.bible.ui.model.FavBookMark
import io.reactivex.disposables.Disposable


interface DbRepository {
    fun getBible(
        success: (LiveData<List<BibleModelEntry>>) -> Unit,
        failure: (ApiError) -> Unit = {},
        terminate: () -> Unit = {}
    ): Disposable

    fun getBibleIndex(
        success: (LiveData<List<BibleIndexModelEntry>>) -> Unit,
        failure: (ApiError) -> Unit = {},
        terminate: () -> Unit = {}
    ): Disposable

    fun getBibleByBookId(
        id: Int,
        success: (LiveData<List<BibleModelEntry>>) -> Unit,
        failure: (ApiError) -> Unit = {},
        terminate: () -> Unit = {}
    ): Disposable

    fun getBibleByBookIdAndChapterId(
        bookId: Int,
        chapterID: Int,
        success: (LiveData<List<BibleModelEntry>>) -> Unit,
        failure: (ApiError) -> Unit = {},
        terminate: () -> Unit = {}
    ): Disposable
    fun getBibleByBookIdAndChapterIdAndVerse(
        bookId: Int,
        chapterID: Int,
        verseId: Int,
        success: (LiveData<List<BibleModelEntry>>) -> Unit,
        failure: (ApiError) -> Unit = {},
        terminate: () -> Unit = {}
    ): Disposable
    fun getAllFav(
        success: (LiveData<List<FavoriteModelEntry>>) -> Unit,
        failure: (ApiError) -> Unit = {},
        terminate: () -> Unit = {}
    ): Disposable
    fun getAllFavHigh(
        success: (List<FavBookMark>) -> Unit,
        failure: (ApiError) -> Unit = {},
        terminate: () -> Unit = {}
    ): Disposable
    fun getFavById(
        id:Int,
        success: (LiveData<FavoriteModelEntry>) -> Unit,
        failure: (ApiError) -> Unit = {},
        terminate: () -> Unit = {}
    ): Disposable

    fun getAllHighlights(
        success: (LiveData<List<HighlightModelEntry>>) -> Unit,
        failure: (ApiError) -> Unit = {},
        terminate: () -> Unit = {}
    ): Disposable
    fun getAllNotes(
        success: (LiveData<List<NoteModelEntry>>) -> Unit,
        failure: (ApiError) -> Unit = {},
        terminate: () -> Unit = {}
    ): Disposable

    fun getNotesById(
        id:Int,
        success: (LiveData<NoteModelEntry>) -> Unit,
        failure: (ApiError) -> Unit = {},
        terminate: () -> Unit = {}
    ): Disposable

    fun insertAllFav(
        favList: ArrayList<FavoriteModelEntry>,
        success: (Unit) -> Unit,
        failure: (ApiError) -> Unit = {},
        terminate: () -> Unit = {}
    ): Disposable

    fun insertAllNotes(
        noteList: ArrayList<NoteModelEntry>,
        success: (Unit) -> Unit,
        failure: (ApiError) -> Unit = {},
        terminate: () -> Unit = {}
    ): Disposable

    fun insertAllHighlight(
        highlights: ArrayList<HighlightModelEntry>,
        success: (Unit) -> Unit,
        failure: (ApiError) -> Unit = {},
        terminate: () -> Unit = {}
    ): Disposable

    fun getHighlightById(
        id:Int,
        success: (LiveData<HighlightModelEntry>) -> Unit,
        failure: (ApiError) -> Unit = {},
        terminate: () -> Unit = {}
    ): Disposable


    fun deleteHighlight(
        id: Int,
        success: (Unit) -> Unit,
        failure: (ApiError) -> Unit = {},
        terminate: () -> Unit = {}
    ): Disposable
    fun deleteNote(
        id: Int,
        success: (Unit) -> Unit,
        failure: (ApiError) -> Unit = {},
        terminate: () -> Unit = {}
    ): Disposable
    fun deleteFavorite(
        id: Int,
        success: (Unit) -> Unit,
        failure: (ApiError) -> Unit = {},
        terminate: () -> Unit = {}
    ): Disposable

    fun getAllLyrics(
        success: (List<LyricsModel>) -> Unit,
        failure: (ApiError) -> Unit = {},
        terminate: () -> Unit = {}
    ): Disposable

}