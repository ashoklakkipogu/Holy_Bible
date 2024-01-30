package com.ashok.myapplication.data.local.dao


import androidx.room.*
import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import com.ashok.myapplication.data.local.entry.BibleModelEntry

@Dao
interface BibleDao {

    @Query("SELECT * FROM bible")
    fun getAllBibleData(): PagingSource<Int, BibleModelEntry>
    @Query("SELECT * FROM bible")
    fun getAllBibleContent(): List<BibleModelEntry>?

    @Query("SELECT * FROM bible WHERE Book =:bookId")
    fun getAllBibleContentByBookId(bookId: Int): LiveData<List<BibleModelEntry>>

    @Query("SELECT * FROM bible WHERE Book = :bookId AND Chapter =:chapterId AND Versecount = :verseId")
    fun getBibleScrollPosition(
        bookId: Int,
        chapterId: Int,
        verseId: Int
    ): BibleModelEntry?
    @Query("SELECT * FROM bible WHERE book = :bookId ORDER BY chapter DESC LIMIT 1")
    fun getBibleScrollLastPosition(
        bookId: Int
    ): BibleModelEntry?




    @Query("SELECT * FROM bible WHERE Book =:bookId AND Chapter =:chapterId")
    fun getAllBibleContentByBookIdAndChapterId(
        bookId: Int,
        chapterId: Int
    ): LiveData<List<BibleModelEntry>>

    @Query("SELECT * FROM bible WHERE Book =:bookId AND Chapter =:chapterId AND Versecount =:verseId")
    fun getAllBibleContentByBookIdAndChapterIdAndVerse(
        bookId: Int,
        chapterId: Int,
        verseId: Int
    ): LiveData<List<BibleModelEntry>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertBibleContent(bible: List<BibleModelEntry>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBible(bible: BibleModelEntry)

    @Delete
    fun deleteBibleContent(bible: BibleModelEntry)

}