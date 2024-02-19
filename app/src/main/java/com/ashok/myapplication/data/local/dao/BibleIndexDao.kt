package com.ashok.myapplication.data.local.dao


import androidx.room.*
import androidx.lifecycle.LiveData
import com.ashok.myapplication.data.local.entry.BibleIndexModelEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface BibleIndexDao {
    @Query("SELECT * FROM bible_index")
    fun getAllBibleIndexContent(): List<BibleIndexModelEntry>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBibleIndex(bible: List<BibleIndexModelEntry>): List<Long>

    @Delete
    fun deleteBibleIndex(bible: BibleIndexModelEntry)
}