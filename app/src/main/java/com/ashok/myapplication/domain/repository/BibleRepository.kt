package com.ashok.myapplication.domain.repository

import com.ashok.myapplication.data.local.entity.LyricsModel
import com.ashok.myapplication.data.local.entity.QuotesModel
import com.ashok.myapplication.data.local.entity.StoryModel
import com.ashok.myapplication.ui.utilities.Result
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface BibleRepository {
    suspend fun getLyrics(): Flow<Result<Map<String, LyricsModel>?>>
    suspend fun getQuotes():Flow<Result<Map<String, List<QuotesModel>>?>>
    suspend fun getStory():Flow<Result<Map<String, StoryModel>?>>
}