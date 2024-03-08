package com.ashok.myapplication.data.local.dao


import androidx.room.*
import androidx.lifecycle.LiveData
import com.ashok.myapplication.data.local.entry.BibleIndexModelEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface BibleIndexDao {
    @Query("SELECT *, (SELECT Chapter FROM bible WHERE Book=bible_index.chapter_id ORDER BY Chapter DESC LIMIT 1) as bibleChapterCount from bible_index where langauge =:langauge")
    suspend fun getAllBibleIndexContent(langauge:String): List<BibleIndexModelEntry>

    @Query("SELECT * from bible_index WHERE langauge = :langauge LIMIT 1")
    suspend fun getLanguage(langauge:String): List<BibleIndexModelEntry>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBibleIndex(bible: List<BibleIndexModelEntry>): List<Long>

    @Delete
    suspend fun deleteBibleIndex(bible: BibleIndexModelEntry)
}