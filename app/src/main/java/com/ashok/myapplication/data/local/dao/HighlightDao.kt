package com.ashok.myapplication.data.local.dao

import androidx.room.*
import androidx.lifecycle.LiveData
import com.ashok.myapplication.data.local.entry.HighlightModelEntry
import com.ashok.myapplication.data.local.model.HighlightModel

@Dao
interface HighlightDao {
    @Query("SELECT bible.Book, bible.Chapter, bible.Versecount, bible.verse,bible.langauge,bible.bibleIndex, highlights.createdDate, highlights.colorCode, highlights.bibleId, highlights.bibleLangIndex, highlights.id FROM highlights LEFT JOIN bible ON bible.id = highlights.bibleId")
    suspend fun getAllHighlight(): List<HighlightModel>?

    @Query("SELECT * FROM highlights")
    suspend fun  getAllHighlightList(): List<HighlightModelEntry>

   /* @Query("SELECT * FROM highlights WHERE Book =:bookId")
    suspend fun getAllHighlightByBookId(bookId: Int): LiveData<List<HighlightModelEntry>>


    @Query("SELECT * FROM highlights WHERE Book =:bookId AND Chapter =:chapterId")
    suspend fun getAllHighlightBookIdAndChapterId(bookId: Int, chapterId: Int): LiveData<List<HighlightModelEntry>>

    @Query("SELECT * FROM highlights WHERE Book =:bookId AND Chapter =:chapterId AND Versecount =:verseId")
    suspend fun getAllHighlightByBookIdAndChapterIdAndVerse(bookId: Int, chapterId: Int, verseId: Int): LiveData<List<HighlightModelEntry>>
*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHighlight(highlight: List<HighlightModelEntry>)

    @Delete
    suspend fun deleteHighlight(highlight: HighlightModelEntry)

    @Query("DELETE FROM highlights WHERE bibleLangIndex = :bibleLangIndex")
    suspend fun deleteHighlightByBibleLangIndex(bibleLangIndex: String)

    @Query("SELECT * FROM highlights WHERE bibleId =:bibleId")
    fun getHighlightById(bibleId: Int): LiveData<HighlightModelEntry>


    @Query("DELETE FROM highlights WHERE id = :id")
    suspend fun deleteHighlightById(id: Int)


}