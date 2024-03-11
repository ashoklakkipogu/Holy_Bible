package com.ashok.bible.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ashok.bible.data.local.entity.LyricsModel

@Dao
interface LyricsDao {
    @Query("SELECT * FROM lyrics")
    fun getAllLyrics(): LiveData<List<LyricsModel>>

    @Query("SELECT * FROM lyrics")
    suspend fun  getAllLyricsList(): List<LyricsModel>

    @Query("SELECT * FROM lyrics LIMIT 1")
    suspend fun  getAllLyricsLimit(): List<LyricsModel>

    @Query("SELECT * FROM lyrics order by title ASC LIMIT :limit OFFSET :offset")
    suspend fun  getAllLyricsByLimit(limit:Int, offset:Int): List<LyricsModel>

    @Query("SELECT * FROM lyrics WHERE title LIKE :title OR titleEn LIKE :title order by title ASC LIMIT :limit OFFSET :offset")
    suspend fun getSongsByTitle(limit:Int, offset:Int, title: String): List<LyricsModel>

    @Query("SELECT * FROM lyrics WHERE title LIKE :title OR titleEn LIKE :title order by titleEn ASC LIMIT :limit OFFSET :offset")
    suspend fun getSongsByTitleEn(limit:Int, offset:Int, title: String): List<LyricsModel>

    @Query("SELECT * FROM lyrics WHERE lyricId =:id OR content = :content")
    suspend fun getSongsById(id: String, content:String): LyricsModel?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLyrics(lyricsModels: LyricsModel)

    @Delete
    suspend fun deleteLyrics(lyricsModels: LyricsModel)


}