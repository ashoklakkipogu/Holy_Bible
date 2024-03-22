package com.ashok.bible.domain.usecase

import androidx.compose.ui.graphics.Color
import com.ashok.bible.data.error.Result
import com.ashok.bible.data.error.RootError
import com.ashok.bible.data.local.entity.BaseModel
import com.ashok.bible.data.local.entity.LyricsModel
import com.ashok.bible.data.local.entity.StatusEmptyImagesModel
import com.ashok.bible.data.local.entity.StatusImagesModel
import com.ashok.bible.data.local.entity.StoryModel
import com.ashok.bible.data.local.entity.UserModel
import com.ashok.bible.domain.RequestState
import com.ashok.bible.domain.model.QuotesMappingModel
import com.ashok.bible.domain.repository.BibleRepository
import com.ashok.bible.ui.discovery.model.ImageGrid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class BibleUseCase @Inject constructor(val repository: BibleRepository) {

    //suspend fun getQuotes(): Flow<RequestState<Map<String, List<QuotesModel>>?>>

    fun getLyrics(): Flow<RequestState<List<LyricsModel>?>> = wrap(
        api = {
            repository.getLyrics()
        },
        onSuccessMap = {
            val list = ArrayList<LyricsModel>()
            if (it != null)
                for ((key, value) in it) {
                    val sample: LyricsModel = value
                    sample.lyricId = key
                    list.add(sample)
                }
            list
        }
    )

    fun getStory(): Flow<RequestState<List<StoryModel>?>> = wrap(
        api = {
            repository.getStory()
        },
        onSuccessMap = {
            var newList: List<StoryModel>? = emptyList()
            if (it != null) {
                val list = ArrayList<StoryModel>()
                for ((_, value) in it) {
                    list.add(value)
                }
                newList = list.sortedBy { sort ->
                    sort.createdDate
                }.reversed()

            }
            newList
        }
    )

    fun getStatusImages(): Flow<RequestState<List<StatusImagesModel>?>> = wrap(
        api = {
            repository.getStatusImages()
        },
        onSuccessMap = {
            var newList: List<StatusImagesModel>? = emptyList()
            if (it != null) {
                val list = ArrayList<StatusImagesModel>()
                for ((_, value) in it) {
                    list.add(value)
                }
                newList = list.sortedBy { sort ->
                    sort.createdDate
                }.reversed()
            }
            newList
        }
    )

    fun saveUsers(userModel: UserModel): Flow<RequestState<BaseModel?>> = wrap(
        api = {
            repository.saveUsers(userModel)
        },
        onSuccessMap = {
            it
        }
    )

    fun getQuotes(): Flow<RequestState<QuotesMappingModel>> = wrap(
        api = {
            repository.getQuotes()
        },
        onSuccessMap = {
            val modelList: ArrayList<ImageGrid> = ArrayList()
            it?.forEach { (key, value) ->
                modelList.add(ImageGrid(title = key, color = Color(value[0].color)))
            }
            val model = QuotesMappingModel()
            model.quotesMap = it
            model.quotesTitles = modelList
            model
        }
    )


    fun getStatusEmptyImages(): Flow<RequestState<List<StatusEmptyImagesModel>?>> = wrap(
        api = {
            repository.getStatusEmptyImages()
        },
        onSuccessMap = {
            var newList: List<StatusEmptyImagesModel>? = emptyList()
            if (it != null) {
                val list = ArrayList<StatusEmptyImagesModel>()
                for ((_, value) in it) {
                    list.add(value)
                }
                newList = list.sortedBy { sort ->
                    sort.createdDate
                }.reversed()
            }
            newList
        }
    )

    private fun <T, D> wrap(
        api: suspend () -> Result<T?, RootError>,
        onSuccessMap: (T?) -> D,
    ): Flow<RequestState<D>> {
        return flow {
            emit(RequestState.Loading)
            when (val result = api()) {
                is Result.Error -> {
                    emit(RequestState.Error(result.error))
                }

                is Result.Success -> {
                    val data: D = onSuccessMap(result.data)
                    emit(RequestState.Success(data))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

}