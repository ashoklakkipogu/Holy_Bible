package com.ashok.myapplication.domain.repository

import com.ashok.myapplication.data.local.entity.BaseModel
import com.ashok.myapplication.data.local.entity.LyricsModel
import com.ashok.myapplication.data.local.entity.QuotesModel
import com.ashok.myapplication.data.local.entity.StatusEmptyImagesModel
import com.ashok.myapplication.data.local.entity.StatusImagesModel
import com.ashok.myapplication.data.local.entity.StoryModel
import com.ashok.myapplication.data.local.entity.UserModel
import com.ashok.myapplication.ui.utilities.Result
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface BibleRepository {
    suspend fun getLyrics(): Flow<Result<Map<String, LyricsModel>?>>
    suspend fun getQuotes():Flow<Result<Map<String, List<QuotesModel>>?>>
    suspend fun getStory():Flow<Result<Map<String, StoryModel>?>>
    suspend fun getStatusImages():Flow<Result<Map<String, StatusImagesModel>?>>
    suspend fun getStatusEmptyImages():Flow<Result<Map<String, StatusEmptyImagesModel>?>>
    suspend fun saveUsers(userModel: UserModel):Flow<Result<BaseModel?>>
}