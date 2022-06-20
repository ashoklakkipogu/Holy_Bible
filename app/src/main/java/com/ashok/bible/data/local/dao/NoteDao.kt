package com.ashok.bible.data.local.dao


import androidx.lifecycle.LiveData
import androidx.room.*
import com.ashok.bible.data.local.entry.FavoriteModelEntry
import com.ashok.bible.data.local.entry.NoteModelEntry
import io.reactivex.Observable

@Dao
interface NoteDao {
    @Query("SELECT * FROM note")
    fun getAllNote(): LiveData<List<NoteModelEntry>>

    @Query("SELECT * FROM note WHERE Book =:bookId")
    fun getAllNoteByBookId(bookId: Int): LiveData<List<NoteModelEntry>>


    @Query("SELECT * FROM note WHERE Book =:bookId AND Chapter =:chapterId")
    fun getAllNoteBookIdAndChapterId(bookId: Int, chapterId: Int): LiveData<List<NoteModelEntry>>

    @Query("SELECT * FROM note WHERE Book =:bookId AND Chapter =:chapterId AND Versecount =:verseId")
    fun getAllNoteByBookIdAndChapterIdAndVerse(bookId: Int, chapterId: Int, verseId: Int): LiveData<List<NoteModelEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note: List<NoteModelEntry>)

    @Delete
    fun deleteNote(note: NoteModelEntry)

    @Query("DELETE FROM note WHERE id = :id")
    fun deleteNoteById(id: Int)

    @Query("SELECT * FROM note WHERE bibleId =:bibleId")
    fun getNotesById(bibleId: Int): LiveData<NoteModelEntry>
}