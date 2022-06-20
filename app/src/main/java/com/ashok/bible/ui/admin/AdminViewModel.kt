package com.ashok.bible.ui.admin

import androidx.lifecycle.MutableLiveData
import com.ashok.bible.data.local.repositary.DbRepository
import com.ashok.bible.data.remote.model.BannerModel
import com.ashok.bible.data.remote.model.BaseModel
import com.ashok.bible.data.remote.model.LyricsModel
import com.ashok.bible.data.remote.model.QuotesModel
import com.ashok.bible.data.remote.network.ApiError
import com.ashok.bible.data.remote.repositary.AppRepoImp
import com.ashok.bible.ui.base.BaseViewModel
import javax.inject.Inject

class AdminViewModel @Inject constructor(
    private val dbRepository: DbRepository,
    private val appRepository: AppRepoImp
) :
    BaseViewModel() {


    val error: MutableLiveData<ApiError> by lazy { MutableLiveData<ApiError>() }
    val lyricData: MutableLiveData<List<LyricsModel>> by lazy { MutableLiveData<List<LyricsModel>>() }
    val createQuotes: MutableLiveData<BaseModel> by lazy { MutableLiveData<BaseModel>() }
    val createBanner: MutableLiveData<BaseModel> by lazy { MutableLiveData<BaseModel>() }

    fun getLyrics() {
        appRepository.getLyrics(
            {
                lyricData.value = it
            },
            {
                error.value = it
            }
        ).also { compositeDisposable.add(it) }
    }

    fun createQuotes(obj: QuotesModel) {
        appRepository.createQuotes(obj, {
            createQuotes.value = it

        }, {
            error.value = it
        }).also { compositeDisposable.add(it) }
    }

    fun createBanner(obj: BannerModel) {
        appRepository.createBanner(obj, {
            createBanner.value = it

        }, {
            error.value = it
        }).also { compositeDisposable.add(it) }
    }
}