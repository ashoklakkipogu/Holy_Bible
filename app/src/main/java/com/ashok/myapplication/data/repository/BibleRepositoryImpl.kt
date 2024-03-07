package com.ashok.myapplication.data.repository

import android.content.SharedPreferences
import androidx.compose.ui.text.toLowerCase
import com.ashok.myapplication.data.remote.ApiService
import com.ashok.myapplication.data.local.entity.LyricsModel
import com.ashok.myapplication.data.local.entity.QuotesModel
import com.ashok.myapplication.data.local.entity.StatusImagesModel
import com.ashok.myapplication.data.local.entity.StoryModel
import com.ashok.myapplication.domain.repository.BibleRepository
import com.ashok.myapplication.ui.utilities.Result
import com.ashok.myapplication.ui.utilities.SharedPrefUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class BibleRepositoryImpl @Inject constructor(
    val api: ApiService,
    val pref: SharedPreferences
) : BibleRepository {
    override suspend fun getLyrics(): Flow<Result<Map<String, LyricsModel>?>> = wrap {
        api.getLyrics()
    }.flowOn(Dispatchers.IO)

    override suspend fun getQuotes(): Flow<Result<Map<String, List<QuotesModel>>?>> =
        wrap {
            val lang = SharedPrefUtils.getLanguage(pref)!!.lowercase()
            api.getQuotes(lang)
        }.flowOn(Dispatchers.IO)

    override suspend fun getStory(): Flow<Result<Map<String, StoryModel>?>> = wrap {
        api.getStories()
    }.flowOn(Dispatchers.IO)

    override suspend fun getStatusImages(): Flow<Result<Map<String, StatusImagesModel>?>> = wrap {
        api.getStatus()
    }.flowOn(Dispatchers.IO)
    /* {
        return try {
            val result = api.getQuotes(lang)
            if (result.isSuccessful) {
                Result.Success(result.body())
            } else {
                Result.Error(result.message())
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Result.Error(
                message = e.message.toString()
            )
        } catch (e: HttpException) {
            e.printStackTrace()
            Result.Error(
                message = e.message.toString()
            )
        }
    }*/

    private fun <T : Any> wrap(function: suspend () -> Response<T>): Flow<Result<T?>> {
        return flow {
            emit(Result.Loading(true))
            val response = function()
            try {
                response.body()?.let {
                    if (response.isSuccessful) {
                        emit(Result.Success(it))
                    } else {
                        emit(Result.Error(response.message()))
                    }
                }
            } catch (e: IOException) {
                emit(
                    Result.Error(
                        message = e.message.toString()
                    )
                )
            } catch (e: HttpException) {
                emit(
                    Result.Error(
                        message = e.message.toString()
                    )
                )
            }
        }
    }
}
