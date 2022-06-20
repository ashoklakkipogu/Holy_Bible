package com.ashok.bible.data.local.dao


import androidx.lifecycle.LiveData
import androidx.room.*
import com.ashok.bible.data.local.entry.FavoriteModelEntry
import io.reactivex.Observable

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite")
    fun getAllFavorites(): LiveData<List<FavoriteModelEntry>>

    @Query("SELECT * FROM favorite")
    fun getAllFavoritesList(): List<FavoriteModelEntry>


    @Query("SELECT * FROM favorite WHERE Book =:bookId")
    fun getAllFavoritesByBookId(bookId: Int): LiveData<List<FavoriteModelEntry>>


    @Query("SELECT * FROM favorite WHERE Book =:bookId AND Chapter =:chapterId")
    fun getAllFavoritesBookIdAndChapterId(bookId: Int, chapterId: Int): LiveData<List<FavoriteModelEntry>>

    @Query("SELECT * FROM favorite WHERE Book =:bookId AND Chapter =:chapterId AND Versecount =:verseId")
    fun getAllFavoritesByBookIdAndChapterIdAndVerse(bookId: Int, chapterId: Int, verseId: Int): LiveData<List<FavoriteModelEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorites(favorite: List<FavoriteModelEntry>)

    @Delete
    fun deleteFavorites(favorite: FavoriteModelEntry)

    @Query("DELETE FROM favorite WHERE id = :id")
    fun deleteFavoriteById(id: Int)

    @Query("SELECT * FROM favorite WHERE bibleId =:bibleId")
    fun getFavoritesById(bibleId: Int): LiveData<FavoriteModelEntry>

}