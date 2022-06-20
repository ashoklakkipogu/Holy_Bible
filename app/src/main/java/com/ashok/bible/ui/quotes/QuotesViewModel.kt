package com.ashok.bible.ui.quotes

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import com.ashok.bible.data.local.repositary.DbRepository
import com.ashok.bible.data.remote.model.QuotesModel
import com.ashok.bible.data.remote.network.ApiError
import com.ashok.bible.data.remote.repositary.AppRepository
import com.ashok.bible.ui.base.BaseViewModel
import javax.inject.Inject


class QuotesViewModel @Inject constructor(
    private val appRepository: AppRepository,
    private val pref: SharedPreferences,
    private val dbRepository: DbRepository
) : BaseViewModel() {

    val quotesData: MutableLiveData<Map<String, List<QuotesModel>>> by lazy { MutableLiveData<Map<String, List<QuotesModel>>>() }
    val error: MutableLiveData<ApiError> by lazy { MutableLiveData<ApiError>() }

    fun getQuotes(lang:String) {
        appRepository.getQuotes(
                lang,
                {
                    quotesData.value = it
                },
                {
                    error.value = it
                }
        ).also { compositeDisposable.add(it) }
    }

}
