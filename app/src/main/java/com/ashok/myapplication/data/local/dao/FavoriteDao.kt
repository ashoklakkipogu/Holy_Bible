package com.ashok.myapplication.data.local.dao

import androidx.room.*
import androidx.lifecycle.LiveData
import com.ashok.myapplication.data.local.entry.FavoriteModelEntry
import com.ashok.myapplication.data.local.model.FavModel

@Dao
interface FavoriteDao {
    @Query("SELECT bible.Book, bible.Chapter, bible.Versecount, bible.verse,bible.langauge,bible.bibleIndex, favorite.createdDate, favorite.bibleId, favorite.bibleLangIndex, favorite.id FROM favorite LEFT JOIN bible ON bible.id = favorite.bibleId")
    suspend fun getAllFavorites(): List<FavModel>?

    @Query("SELECT * FROM favorite")
    suspend fun getAllFavoritesList(): List<FavoriteModelEntry>


    /*@Query("SELECT * FROM favorite WHERE Book =:bookId")
    suspend fun getAllFavoritesByBookId(bookId: Int): LiveData<List<FavoriteModelEntry>>


    @Query("SELECT * FROM favorite WHERE Book =:bookId AND Chapter =:chapterId")
    suspend fun getAllFavoritesBookIdAndChapterId(bookId: Int, chapterId: Int): LiveData<List<FavoriteModelEntry>>

    @Query("SELECT * FROM favorite WHERE Book =:bookId AND Chapter =:chapterId AND Versecount =:verseId")
    suspend fun getAllFavoritesByBookIdAndChapterIdAndVerse(bookId: Int, chapterId: Int, verseId: Int): LiveData<List<FavoriteModelEntry>>*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorites(favorite: List<FavoriteModelEntry>)

    @Delete
    suspend fun deleteFavorites(favorite: FavoriteModelEntry)

    @Query("DELETE FROM favorite WHERE id = :id")
    suspend fun deleteFavoriteById(id: Int)

    @Query("DELETE FROM favorite WHERE bibleLangIndex = :bibleLangIndex")
    suspend fun deleteFavoriteByBibleLangIndex(bibleLangIndex: String)

}