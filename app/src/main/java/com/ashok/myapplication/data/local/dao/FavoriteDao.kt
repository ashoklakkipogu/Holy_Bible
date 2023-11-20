package com.ashok.myapplication.data.local.dao

import androidx.room.*
import androidx.lifecycle.LiveData
import com.ashok.myapplication.data.local.entry.FavoriteModelEntry

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