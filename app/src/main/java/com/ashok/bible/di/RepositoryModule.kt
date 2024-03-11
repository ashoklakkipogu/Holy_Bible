package com.ashok.bible.di

import android.content.SharedPreferences
import com.ashok.bible.data.remote.ApiService
import com.ashok.bible.data.repository.BibleRepositoryImpl
import com.ashok.bible.domain.repository.BibleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideProductDataSource(
        apiService: ApiService, pref: SharedPreferences
    ): BibleRepository {
        return BibleRepositoryImpl(apiService, pref)
    }
}