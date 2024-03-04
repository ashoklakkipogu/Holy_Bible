package com.ashok.myapplication.data.local.dao


import androidx.room.*
import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import com.ashok.myapplication.data.local.entry.BibleModelEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface BibleDao {

    @Query("SELECT b.Book, b.chapter, b.Versecount, b.verse, b.langauge, b.bibleId, b.bibleLangIndex, b.bibleIndex, b.id, case when favorite.bibleId is null then 0 else 1 end as isBookMark, case when note.bibleId is null then 0 else 1 end as isNote, case when highlights.colorCode is null then \"\" else highlights.colorCode end as selectedBackground FROM bible as b LEFT JOIN favorite ON favorite.bibleId = b.id LEFT JOIN highlights ON highlights.bibleId = b.id LEFT JOIN note ON note.bibleId = b.id")
    fun getAllBibleData(): PagingSource<Int, BibleModelEntry>

    @Query("SELECT * FROM bible")
    fun getAllBibleContent(): List<BibleModelEntry>?

    @Query("SELECT * FROM bible WHERE Book =:bookId AND langauge =:langauge")
    suspend fun getBibleChaptersByBookIdAndLangauge(bookId: Int, langauge:String): List<BibleModelEntry>

    @Query("SELECT * FROM bible WHERE Book = :bookId AND Chapter =:chapterId AND Versecount = :verseId")
    suspend fun getBibleScrollPosition(
        bookId: Int,
        chapterId: Int,
        verseId: Int
    ): BibleModelEntry?

    @Query("SELECT * FROM bible WHERE book = :bookId ORDER BY chapter DESC LIMIT 1")
    suspend fun getBibleScrollLastPosition(
        bookId: Int
    ): BibleModelEntry?


    @Query("SELECT * FROM bible WHERE Book =:bookId AND Chapter =:chapterId AND langauge =:langauge")
    suspend fun getBibleVerseByBookIdAndChapterId(
        bookId: Int,
        chapterId: Int,
        langauge:String
    ): List<BibleModelEntry>

    @Query("SELECT * FROM bible WHERE Book =:bookId AND Chapter =:chapterId AND Versecount =:verseId")
    fun getAllBibleContentByBookIdAndChapterIdAndVerse(
        bookId: Int,
        chapterId: Int,
        verseId: Int
    ): LiveData<List<BibleModelEntry>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBibleContent(bible: List<BibleModelEntry>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBible(bible: BibleModelEntry)

    @Delete
    suspend fun deleteBibleContent(bible: BibleModelEntry)

    @Query("SELECT b.Book, b.chapter, b.Versecount, b.verse, b.langauge, b.bibleId, b.bibleLangIndex, b.bibleIndex, b.id, case when favorite.bibleId is null then 0 else 1 end as isBookMark, case when note.bibleId is null then 0 else 1 end as isNote, case when highlights.colorCode is null then \"\" else highlights.colorCode end as selectedBackground FROM bible as b LEFT JOIN favorite ON favorite.bibleId = b.id LEFT JOIN highlights ON highlights.bibleId = b.id LEFT JOIN note ON note.bibleId = b.id WHERE b.Book =:bookId AND b.Chapter =:chapterId AND b.langauge =:langauge")
    suspend fun getBibleListByBookIdAndChapterId(
        bookId: Int,
        chapterId: Int,
        langauge: String
    ): List<BibleModelEntry>?

}