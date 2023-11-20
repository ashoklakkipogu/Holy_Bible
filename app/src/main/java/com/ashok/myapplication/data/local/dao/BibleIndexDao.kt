package com.ashok.myapplication.data.local.dao


import androidx.room.*
import androidx.lifecycle.LiveData
import com.ashok.myapplication.data.local.entry.BibleIndexModelEntry

@Dao
interface BibleIndexDao {
    @Query("SELECT * FROM bible_index")
    fun getAllBibleIndexContent(): LiveData<List<BibleIndexModelEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBibleIndex(bible: List<BibleIndexModelEntry>): List<Long>

    @Delete
    fun deleteBibleIndex(bible: BibleIndexModelEntry)
}