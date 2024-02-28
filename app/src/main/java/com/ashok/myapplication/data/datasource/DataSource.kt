package com.ashok.myapplication.data.datasource

import com.ashok.myapplication.data.entity.LyricsModel
import com.ashok.myapplication.data.entity.Products
import retrofit2.Response

interface DataSource {
    suspend fun getLyrics():Response<Map<String, LyricsModel>>
}