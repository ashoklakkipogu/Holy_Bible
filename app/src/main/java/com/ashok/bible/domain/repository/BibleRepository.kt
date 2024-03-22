package com.ashok.bible.domain.repository

import com.ashok.bible.data.error.Result
import com.ashok.bible.data.error.RootError
import com.ashok.bible.data.local.entity.BaseModel
import com.ashok.bible.data.local.entity.LyricsModel
import com.ashok.bible.data.local.entity.QuotesModel
import com.ashok.bible.data.local.entity.StatusEmptyImagesModel
import com.ashok.bible.data.local.entity.StatusImagesModel
import com.ashok.bible.data.local.entity.StoryModel
import com.ashok.bible.data.local.entity.UserModel

interface BibleRepository {
    suspend fun getLyrics(): Result<Map<String, LyricsModel>?, RootError>
    suspend fun getQuotes(): Result<Map<String, List<QuotesModel>>?, RootError>
    suspend fun getStory(): Result<Map<String, StoryModel>?, RootError>
    suspend fun getStatusImages(): Result<Map<String, StatusImagesModel>?, RootError>
    suspend fun getStatusEmptyImages(): Result<Map<String, StatusEmptyImagesModel>?, RootError>
    suspend fun saveUsers(userModel: UserModel): Result<BaseModel?, RootError>
}