package com.ashok.bible.data.repository

import android.content.SharedPreferences
import android.util.Log
import com.ashok.bible.data.error.DataError
import com.ashok.bible.data.error.Result
import com.ashok.bible.data.error.RootError
import com.ashok.bible.data.local.entity.BaseModel
import com.ashok.bible.data.remote.ApiService
import com.ashok.bible.data.local.entity.LyricsModel
import com.ashok.bible.data.local.entity.QuotesModel
import com.ashok.bible.data.local.entity.StatusEmptyImagesModel
import com.ashok.bible.data.local.entity.StatusImagesModel
import com.ashok.bible.data.local.entity.StoryModel
import com.ashok.bible.data.local.entity.UserModel
import com.ashok.bible.domain.repository.BibleRepository
import com.ashok.bible.ui.utilities.SharedPrefUtils
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

class BibleRepositoryImpl @Inject constructor(
    val api: ApiService, val pref: SharedPreferences
) : BibleRepository {
    private val TAG = BibleRepositoryImpl::class.java.simpleName

    override suspend fun getLyrics(): Result<Map<String, LyricsModel>?, RootError> = wrap {
        val lang = SharedPrefUtils.getLanguage(pref)!!
        api.getLyrics(language = "\"${lang.lowercase()}\"")

    }

    override suspend fun getQuotes(): Result<Map<String, List<QuotesModel>>?, RootError> = wrap {
        val lang = SharedPrefUtils.getLanguage(pref)!!.lowercase()
        api.getQuotes(lang)
    }

    override suspend fun getStory(): Result<Map<String, StoryModel>?, RootError> = wrap {
        val lang = SharedPrefUtils.getLanguage(pref)!!
        api.getStories(language = "\"$lang\"")
    }

    override suspend fun getStatusImages(): Result<Map<String, StatusImagesModel>?, RootError> =
        wrap {
            val lang = SharedPrefUtils.getLanguage(pref)!!
            api.getStatus(language = "\"$lang\"")
        }


    override suspend fun getStatusEmptyImages(): Result<Map<String, StatusEmptyImagesModel>?, RootError> =
        wrap {
            api.getStatusEmptyImages()
        }

    override suspend fun saveUsers(userModel: UserModel): Result<BaseModel?, RootError> = wrap {
        api.saveUsers(userModel)
    }

    /*private fun <T : Any> wrap(function: suspend () -> Response<T>): Flow<RequestState<T?>> {
        return flow {
            emit(RequestState.Loading)
            try {
                emit(RequestState.Success(function().body()))
            } catch (throwable: Throwable) {
                emit(onError(throwable))
            }
        }.flowOn(Dispatchers.IO)
    }

    private fun <T> onError(e: Throwable): RequestState<T?> {
        Log.d(TAG, "onError: $e")
        return when (e) {
            is HttpException -> {
                RequestState.Error(
                    DataError.Network.BAD_RESPONSE
                )
            }

            is SocketTimeoutException -> {
                RequestState.Error(DataError.Network.TIMEOUT)
            }

            is IOException -> {
                RequestState.Error(DataError.Network.NO_CONNECTION)
            }

            is NullPointerException -> {
                RequestState.Error(DataError.Network.EMPTY_RESPONSE)
            }

            else -> RequestState.Error(DataError.Network.NOT_DEFINED)
        }
    }*/


    private suspend fun <T> wrap(function: suspend () -> Response<T>): Result<T?, DataError> {
        return try {
            Result.Success(function().body())
        } catch (throwable: Throwable) {
            onError(throwable)
        }

    }

    private fun <T> onError(e: Throwable): Result<T?, DataError> {
        Log.d(TAG, "onError: $e")
        return when (e) {
            is HttpException -> {
                Result.Error(
                    com.ashok.bible.data.error.DataError.Network.BAD_RESPONSE
                )
            }

            is SocketTimeoutException -> {
                Result.Error(DataError.Network.TIMEOUT)
            }

            is IOException -> {
                Result.Error(DataError.Network.NO_CONNECTION)
            }

            is NullPointerException -> {
                Result.Error(DataError.Network.EMPTY_RESPONSE)
            }

            else -> Result.Error(DataError.Network.NOT_DEFINED)
        }
    }

    private fun getErrorMessage(errorBody: ResponseBody?): String = try {
        val result = errorBody?.string()
        Log.d(TAG, "getErrorMessage() called with: errorBody = [$result]")
        val json = Gson().fromJson(result, JsonObject::class.java)
        json.toString()
    } catch (t: Throwable) {
        ""
    }
}
