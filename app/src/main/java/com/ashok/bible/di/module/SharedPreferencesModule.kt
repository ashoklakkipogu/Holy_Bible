package com.ashok.bible.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.ashok.bible.common.AppConstants
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SharedPreferencesModule {
    @Provides
    @Singleton
    fun provideSharedPreferencesModule(application: Application): SharedPreferences {
        return application.getSharedPreferences(AppConstants.SHARED_PREF, Context.MODE_PRIVATE)
    }
}