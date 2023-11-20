package com.ashok.myapplication.data.local


import androidx.room.*
import android.content.SharedPreferences
import com.ashok.myapplication.data.local.dao.BibleDao
import com.ashok.myapplication.data.local.dao.BibleIndexDao
import com.ashok.myapplication.data.local.dao.LyricsDao
import com.ashok.myapplication.data.local.dao.FavoriteDao
import com.ashok.myapplication.data.local.dao.HighlightDao
import com.ashok.myapplication.data.local.dao.NoteDao
import com.ashok.myapplication.data.entity.LyricsModel
import com.ashok.myapplication.data.local.entry.BibleIndexModelEntry
import com.ashok.myapplication.data.local.entry.BibleModelEntry
import com.ashok.myapplication.data.local.entry.FavoriteModelEntry
import com.ashok.myapplication.data.local.entry.HighlightModelEntry
import com.ashok.myapplication.data.local.entry.NoteModelEntry
import javax.inject.Inject


@Database(
    entities = [BibleModelEntry::class, BibleIndexModelEntry::class, FavoriteModelEntry::class, NoteModelEntry::class, HighlightModelEntry::class, LyricsModel::class],
    exportSchema = false,
    version = BibleDatabase.VERSION
)
abstract class BibleDatabase : RoomDatabase() {
    @Inject
    lateinit var pref: SharedPreferences

    companion object {
        const val VERSION = 2
        const val DATABASE = "Bible.db"
    }

    abstract fun bibleDao(): BibleDao
    abstract fun bibleIndexDao(): BibleIndexDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun noteDao(): NoteDao
    abstract fun highlightDao(): HighlightDao
    abstract fun lyricsDao(): LyricsDao


}