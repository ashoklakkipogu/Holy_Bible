package com.ashok.bible.data.repository

import android.content.SharedPreferences
import android.util.Log
import com.ashok.bible.data.local.entity.BaseModel
import com.ashok.bible.data.remote.ApiService
import com.ashok.bible.data.local.entity.LyricsModel
import com.ashok.bible.data.local.entity.QuotesModel
import com.ashok.bible.data.local.entity.StatusEmptyImagesModel
import com.ashok.bible.data.local.entity.StatusImagesModel
import com.ashok.bible.data.local.entity.StoryModel
import com.ashok.bible.data.local.entity.UserModel
import com.ashok.bible.data.model.ApiError
import com.ashok.bible.data.model.Errors
import com.ashok.bible.domain.repository.BibleRepository
import com.ashok.bible.ui.utilities.Result
import com.ashok.bible.ui.utilities.SharedPrefUtils
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
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

    override suspend fun getLyrics(): Flow<Result<Map<String, LyricsModel>?>> = wrap {
        val lang = SharedPrefUtils.getLanguage(pref)!!
        api.getLyrics(language = "\"${lang.lowercase()}\"")
    }.flowOn(Dispatchers.IO)

    override suspend fun getQuotes(): Flow<Result<Map<String, List<QuotesModel>>?>> = wrap {
        val lang = SharedPrefUtils.getLanguage(pref)!!.lowercase()
        api.getQuotes(lang)
    }.flowOn(Dispatchers.IO)

    override suspend fun getStory(): Flow<Result<Map<String, StoryModel>?>> = wrap {
        val lang = SharedPrefUtils.getLanguage(pref)!!
        api.getStories(language = "\"$lang\"")
    }.flowOn(Dispatchers.IO)

    override suspend fun getStatusImages(): Flow<Result<Map<String, StatusImagesModel>?>> = wrap {
        val lang = SharedPrefUtils.getLanguage(pref)!!
        api.getStatus(language = "\"$lang\"")
    }.flowOn(Dispatchers.IO)


    override suspend fun getStatusEmptyImages(): Flow<Result<Map<String, StatusEmptyImagesModel>?>> =
        wrap {
            api.getStatusEmptyImages()
        }.flowOn(Dispatchers.IO)

    override suspend fun  saveUsers(userModel: UserModel): Flow<Result<BaseModel?>> = wrap {
        api.saveUsers(userModel)
    }.flowOn(Dispatchers.IO)


    private fun <T : Any> wrap(function: suspend () -> Response<T>): Flow<Result<T?>> {
        return flow {
            emit(Result.Loading(true))
            try {
                val response = function()
                //Log.i("response", "response.........remote$response")
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        emit(Result.Success(response.body()))
                    } else
                        emit(Result.Error(ApiError(ApiError.ApiStatus.EMPTY_RESPONSE)))
                } else {
                    emit(Result.Error(ApiError(ApiError.ApiStatus.EMPTY_RESPONSE)))
                }
            } catch (throwable: Throwable) {
                emit(onError(throwable))
            }

            //else -> failure(ApiError(ApiError.ApiStatus.NOT_DEFINED))
        }
    }

    private fun <T> onError(e: Throwable): Result<T?> {
        Log.d(TAG, "onError: $e")
        return when (e) {
            is HttpException -> {
                val errorMessage = getErrorMessage(e.response()?.errorBody())
                var errors = Errors()
                try {
                    errors = Gson().fromJson(errorMessage, Errors::class.java)
                } catch (e: Exception) {
                    //e.printStackTrace()
                }

                Result.Error(
                    ApiError(
                        ApiError.ApiStatus.BAD_RESPONSE, e.code(), errorMessage,
                        errors
                    )
                )
            }

            is SocketTimeoutException -> {
                Result.Error(ApiError(ApiError.ApiStatus.TIMEOUT, message = e.localizedMessage))
            }

            is IOException -> {
                Result.Error(ApiError(ApiError.ApiStatus.NO_CONNECTION, message = e.localizedMessage))
            }

            is NullPointerException -> {
                Result.Error(ApiError(ApiError.ApiStatus.EMPTY_RESPONSE))
            }

            else -> Result.Error(ApiError(ApiError.ApiStatus.NOT_DEFINED))
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
