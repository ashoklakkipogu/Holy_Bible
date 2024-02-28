package com.ashok.myapplication.data.datasource

import com.ashok.myapplication.data.api.ApiService
import com.ashok.myapplication.data.entity.LyricsModel
import com.ashok.myapplication.data.entity.Products
import retrofit2.Response
import javax.inject.Inject

class DataSourceImpl @Inject constructor(val apiService: ApiService): DataSource {
    override suspend fun getLyrics(): Response<Map<String, LyricsModel>> {
        return apiService.getLyrics()
    }
}