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
import javax.inject.Inject

class DbRepoImp @Inject constructor(
    private val bibleDao: BibleDao,
    private val bibleIndexDao: BibleIndexDao,
    private val favoriteDao: FavoriteDao,
    private val noteDao: NoteDao,
    private val highlightDao: HighlightDao,
    private val lyricsDao: LyricsDao
) : DbRepository {
    override suspend fun getBible() = bibleDao.getAllBibleContent()
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

    override suspend fun getBibleIndex() = bibleIndexDao.getAllBibleIndexContent()
    override suspend fun getBibleByBookId(id: Int) = bibleDao.getAllBibleContentByBookId(id)
    override suspend fun getBibleByBookIdAndChapterId(bookId: Int, chapterID: Int) =
        bibleDao.getAllBibleContentByBookIdAndChapterId(bookId, chapterID)

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
    override suspend fun getAllFavHigh(): ArrayList<FavBookMark> {
        val favData = favoriteDao.getAllFavoritesList()
        val highData = highlightDao.getAllHighlightList()
        val list: ArrayList<FavBookMark> = ArrayList()

        for (obj in favData) {
            val favBookMark = FavBookMark()
            favBookMark.book = obj.book
            favBookMark.chapter = obj.chapter
            favBookMark.verse = obj.verse
            favBookMark.versecount = obj.versecount
            favBookMark.bibleId = obj.bibleId
            favBookMark.createdDate = obj.createdDate
            favBookMark.bibleIndexName = obj.bibleIndexName
            //favBookMark.color = obj.col
            favBookMark.favId = obj.id
            favBookMark.isFav = true
            list.add(favBookMark)
        }
        for (obj in highData) {
            val favBookMark = FavBookMark()
            favBookMark.book = obj.book
            favBookMark.chapter = obj.chapter
            favBookMark.verse = obj.verse
            favBookMark.versecount = obj.versecount
            favBookMark.bibleId = obj.bibleId
            favBookMark.createdDate = obj.createdDate
            favBookMark.bibleIndexName = obj.bibleIndexName
            favBookMark.color = obj.color
            favBookMark.highlightId = obj.id
            favBookMark.isFav = false
            list.add(favBookMark)
        }
        if (list.isEmpty()) {
            val favBookMark = FavBookMark()
            favBookMark.noData = true
            list.add(favBookMark)
        }

        var noData = false
        val listFavBookMark: ArrayList<FavBookMark> = ArrayList()
        if (list.size == 1) {
            for (obj in list) {
                noData = obj.noData
            }
        }
        return if (noData) {
            listFavBookMark
        } else {
            listFavBookMark
        }
    }


    override suspend fun getFavById(id: Int) = favoriteDao.getFavoritesById(id)
    override suspend fun getAllHighlights() = highlightDao.getAllHighlight()
    override suspend fun getAllNotes() = noteDao.getAllNote()
    override suspend fun getNotesById(id: Int) = noteDao.getNotesById(id)
    override suspend fun insertAllFav(favList: ArrayList<FavoriteModelEntry>) =
        favoriteDao.insertFavorites(favList)

    override suspend fun insertAllNotes(noteList: ArrayList<NoteModelEntry>) =
        noteDao.insertNote(noteList)

    override suspend fun insertAllHighlight(highlights: ArrayList<HighlightModelEntry>) =
        highlightDao.insertHighlight(highlights)

    override suspend fun getHighlightById(id: Int) = highlightDao.getHighlightById(id)
    override suspend fun deleteHighlight(id: Int) = highlightDao.deleteHighlightById(id)
    override suspend fun deleteNote(id: Int) = noteDao.deleteNoteById(id)
    override suspend fun deleteFavorite(id: Int) = favoriteDao.deleteFavoriteById(id)
    override suspend fun getAllLyrics(id: Int) = lyricsDao.getAllLyricsList()
}