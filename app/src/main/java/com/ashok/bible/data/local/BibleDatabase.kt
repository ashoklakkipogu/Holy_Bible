package com.ashok.bible.data.local

import android.content.SharedPreferences
import androidx.room.Database
import androidx.room.RoomDatabase
import com.ashok.bible.data.local.dao.BibleDao
import com.ashok.bible.data.local.dao.BibleIndexDao
import com.ashok.bible.data.local.dao.LyricsDao
import com.ashok.bible.data.local.entry.*
import com.ashok.bible.data.local.dao.FavoriteDao
import com.ashok.bible.data.local.dao.HighlightDao
import com.ashok.bible.data.local.dao.NoteDao
import com.ashok.bible.data.remote.model.LyricsModel
import javax.inject.Inject


@Database(entities = [BibleModelEntry::class, BibleIndexModelEntry::class, FavoriteModelEntry::class, NoteModelEntry::class, HighlightModelEntry::class, LyricsModel::class], exportSchema = false, version = BibleDatabase.VERSION)
abstract class BibleDatabase: RoomDatabase() {
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