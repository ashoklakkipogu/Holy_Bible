package com.ashok.bible.data.local.dao


import androidx.lifecycle.LiveData
import androidx.room.*
import com.ashok.bible.data.local.entry.BibleIndexModelEntry
import com.ashok.bible.data.local.entry.BibleModelEntry
import io.reactivex.Observable

@Dao
interface BibleIndexDao {
    @Query("SELECT * FROM bible_index")
    fun getAllBibleIndexContent(): LiveData<List<BibleIndexModelEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBibleIndex(bible: List<BibleIndexModelEntry>)

    @Delete
    fun deleteBibleIndex(bible: BibleIndexModelEntry)
}