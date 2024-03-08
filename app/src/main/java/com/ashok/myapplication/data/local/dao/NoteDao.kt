package com.ashok.myapplication.data.local.dao

import androidx.room.*
import androidx.lifecycle.LiveData
import com.ashok.myapplication.data.local.entry.HighlightModelEntry
import com.ashok.myapplication.data.local.entry.NoteModelEntry
import com.ashok.myapplication.data.local.model.NoteModel

@Dao
interface NoteDao {
    @Query("SELECT bible.Book, bible.Chapter, bible.Versecount, bible.verse,bible.langauge,bible.bibleIndex, note.createdDate, note.noteName, note.bibleId, note.bibleLangIndex, note.id FROM note LEFT JOIN bible ON bible.id = note.bibleId WHERE note.langauge=:language")
    suspend fun getAllNote(language:String): List<NoteModel>?

    @Query("SELECT * FROM note")
    suspend fun  getAllNoteList(): List<NoteModelEntry>


    /*@Query("SELECT * FROM note WHERE Book =:bookId")
    suspend fun getAllNoteByBookId(bookId: Int): LiveData<List<NoteModelEntry>>


    @Query("SELECT * FROM note WHERE Book =:bookId AND Chapter =:chapterId")
    suspend fun getAllNoteBookIdAndChapterId(bookId: Int, chapterId: Int): LiveData<List<NoteModelEntry>>

    @Query("SELECT * FROM note WHERE Book =:bookId AND Chapter =:chapterId AND Versecount =:verseId")
    suspend fun getAllNoteByBookIdAndChapterIdAndVerse(bookId: Int, chapterId: Int, verseId: Int): LiveData<List<NoteModelEntry>>
*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: List<NoteModelEntry>)

    @Delete
    suspend fun deleteNote(note: NoteModelEntry)

    @Query("DELETE FROM note WHERE id = :id")
    suspend fun deleteNoteById(id: Int)

    @Query("SELECT * FROM note WHERE bibleId =:bibleId")
    fun getNotesById(bibleId: Int): LiveData<NoteModelEntry>

    @Query("DELETE FROM note WHERE bibleLangIndex = :bibleLangIndex")
    suspend fun deleteNotesByBibleLangIndex(bibleLangIndex: String)
}