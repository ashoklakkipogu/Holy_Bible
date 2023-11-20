package com.ashok.myapplication.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.ashok.myapplication.data.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SharedPreferencesModule {
    @Provides
    @Singleton
    fun provideSharedPreferencesModule(application: Application): SharedPreferences {
        return application.getSharedPreferences(AppConstants.SHARED_PREF, Context.MODE_PRIVATE)
    }
}