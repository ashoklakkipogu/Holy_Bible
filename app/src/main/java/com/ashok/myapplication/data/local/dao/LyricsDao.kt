package com.ashok.myapplication.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ashok.myapplication.data.entity.LyricsModel

@Dao
interface LyricsDao {
    @Query("SELECT * FROM lyrics")
    fun getAllLyrics(): LiveData<List<LyricsModel>>

    @Query("SELECT * FROM lyrics")
    fun  getAllLyricsList(): List<LyricsModel>

    @Query("SELECT * FROM lyrics LIMIT 1")
    fun  getAllLyricsLimit(): List<LyricsModel>

    @Query("SELECT * FROM lyrics order by title ASC LIMIT :limit OFFSET :offset")
    fun  getAllLyricsByLimit(limit:Int, offset:Int): List<LyricsModel>

    @Query("SELECT * FROM lyrics WHERE title LIKE :title OR titleEn LIKE :title order by title ASC LIMIT :limit OFFSET :offset")
    fun getSongsByTitle(limit:Int, offset:Int, title: String): List<LyricsModel>

    @Query("SELECT * FROM lyrics WHERE title LIKE :title OR titleEn LIKE :title order by titleEn ASC LIMIT :limit OFFSET :offset")
    fun getSongsByTitleEn(limit:Int, offset:Int, title: String): List<LyricsModel>

    @Query("SELECT * FROM lyrics WHERE lyricId =:id OR content = :content")
    fun getSongsById(id: String, content:String): LyricsModel?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLyrics(lyricsModels: LyricsModel)

    @Delete
    fun deleteLyrics(lyricsModels: LyricsModel)


}