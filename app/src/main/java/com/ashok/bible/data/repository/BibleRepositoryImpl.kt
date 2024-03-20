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
import com.ashok.bible.data.model.DataError
import com.ashok.bible.data.model.Errors
import com.ashok.bible.domain.RequestState
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

    override suspend fun getLyrics(): Flow<RequestState<Map<String, LyricsModel>?>> = wrap {
        val lang = SharedPrefUtils.getLanguage(pref)!!
        api.getLyrics(language = "\"${lang.lowercase()}\"")

    }.flowOn(Dispatchers.IO)

    override suspend fun getQuotes(): Flow<RequestState<Map<String, List<QuotesModel>>?>> = wrap {
        val lang = SharedPrefUtils.getLanguage(pref)!!.lowercase()
        api.getQuotes(lang)
    }

    override suspend fun getStory(): Flow<RequestState<Map<String, StoryModel>?>> = wrap {
        val lang = SharedPrefUtils.getLanguage(pref)!!
        api.getStories(language = "\"$lang\"")
    }

    override suspend fun getStatusImages(): Flow<RequestState<Map<String, StatusImagesModel>?>> = wrap {
        val lang = SharedPrefUtils.getLanguage(pref)!!
        api.getStatus(language = "\"$lang\"")
    }


    override suspend fun getStatusEmptyImages(): Flow<RequestState<Map<String, StatusEmptyImagesModel>?>> =
        wrap {
            api.getStatusEmptyImages()
        }

    override suspend fun saveUsers(userModel: UserModel): Flow<RequestState<BaseModel?>> = wrap {
        api.saveUsers(userModel)
    }

    private fun <T : Any> wrap(function: suspend () -> Response<T>): Flow<RequestState<T?>> {
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
