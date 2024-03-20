package com.ashok.bible.domain.repository

import com.ashok.bible.data.local.entity.BaseModel
import com.ashok.bible.data.local.entity.LyricsModel
import com.ashok.bible.data.local.entity.QuotesModel
import com.ashok.bible.data.local.entity.StatusEmptyImagesModel
import com.ashok.bible.data.local.entity.StatusImagesModel
import com.ashok.bible.data.local.entity.StoryModel
import com.ashok.bible.data.local.entity.UserModel
import com.ashok.bible.domain.RequestState
import kotlinx.coroutines.flow.Flow

interface BibleRepository {
    suspend fun getLyrics(): Flow<RequestState<Map<String, LyricsModel>?>>
    suspend fun getQuotes():Flow<RequestState<Map<String, List<QuotesModel>>?>>
    suspend fun getStory():Flow<RequestState<Map<String, StoryModel>?>>
    suspend fun getStatusImages():Flow<RequestState<Map<String, StatusImagesModel>?>>
    suspend fun getStatusEmptyImages():Flow<RequestState<Map<String, StatusEmptyImagesModel>?>>
    suspend fun saveUsers(userModel: UserModel):Flow<RequestState<BaseModel?>>
}