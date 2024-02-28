package com.ashok.myapplication.ui.repository

import com.ashok.myapplication.data.datasource.DataSource
import com.ashok.myapplication.data.datasource.UserDataSource
import com.ashok.myapplication.data.entity.LyricsModel
import com.ashok.myapplication.data.entity.Users
import com.ashok.myapplication.ui.utilities.Result
import javax.inject.Inject

class LyricRepository @Inject constructor(private val dataSource: DataSource) {

    suspend fun getAllLyrics(): Result<List<LyricsModel>> {
        val lyrics = dataSource.getLyrics()
        return if (lyrics.isSuccessful && lyrics.body() != null) {
            val data = ArrayList<LyricsModel>()
            val map = lyrics.body()
            if (map != null) {
                for ((key, value) in map) {
                    val sample: LyricsModel = value
                    sample.lyricId = key
                    data.add(sample)
                }
            }
            Result.Success(data)
        } else {
            Result.Error(lyrics.message())
        }
    }
}