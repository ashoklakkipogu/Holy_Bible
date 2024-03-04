package com.ashok.myapplication.di

import android.app.Application
import android.content.SharedPreferences
import androidx.room.Room
import com.ashok.myapplication.data.local.BibleDatabase
import com.ashok.myapplication.data.local.dao.BibleDao
import com.ashok.myapplication.data.local.dao.BibleIndexDao
import com.ashok.myapplication.data.local.dao.LyricsDao
import com.ashok.myapplication.data.local.dao.FavoriteDao
import com.ashok.myapplication.data.local.dao.HighlightDao
import com.ashok.myapplication.data.local.dao.NoteDao
import com.ashok.myapplication.data.repository.DbRepoImp
import com.ashok.myapplication.domain.repository.DbRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Provides
    @Singleton
    fun provideRoomDataBase(application:Application): BibleDatabase =
        Room.databaseBuilder(application, BibleDatabase::class.java, BibleDatabase.DATABASE).build()

    @Provides
    @Singleton
    fun provideNotificationDao(bibleDatabase: BibleDatabase): BibleDao =
        bibleDatabase.bibleDao()

    @Provides
    @Singleton
    fun provideBibleIndexDao(bibleDatabase: BibleDatabase): BibleIndexDao =
        bibleDatabase.bibleIndexDao()

    @Provides
    @Singleton
    fun provideFavoriteDao(bibleDatabase: BibleDatabase): FavoriteDao =
        bibleDatabase.favoriteDao()

    @Provides
    @Singleton
    fun provideNoteDao(bibleDatabase: BibleDatabase): NoteDao =
        bibleDatabase.noteDao()

    @Provides
    @Singleton
    fun provideHighlightDao(bibleDatabase: BibleDatabase): HighlightDao =
        bibleDatabase.highlightDao()

    @Provides
    @Singleton
    fun provideLyricDao(bibleDatabase: BibleDatabase): LyricsDao =
        bibleDatabase.lyricsDao()


    @Provides
    fun provideDbRepository(bibleDao: BibleDao, bibleIndexDao: BibleIndexDao, favoriteDao: FavoriteDao, noteDao: NoteDao, highlightDao: HighlightDao, lyricsDao: LyricsDao, pref: SharedPreferences): DbRepository {
        return DbRepoImp(bibleDao, bibleIndexDao, favoriteDao, noteDao, highlightDao, lyricsDao, pref)
    }


}