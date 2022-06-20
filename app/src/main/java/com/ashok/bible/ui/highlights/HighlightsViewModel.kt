package com.ashok.bible.ui.highlights

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.ashok.bible.data.local.entry.HighlightModelEntry
import com.ashok.bible.data.local.repositary.DbRepository
import com.ashok.bible.data.remote.network.ApiError
import com.ashok.bible.ui.base.BaseViewModel
import javax.inject.Inject

class HighlightsViewModel @Inject constructor(
    private val dbRepository: DbRepository
) :
    BaseViewModel() {

    val error: MutableLiveData<ApiError> by lazy { MutableLiveData<ApiError>() }
    val highlightsData: MutableLiveData<List<HighlightModelEntry>> by lazy { MutableLiveData<List<HighlightModelEntry>>() }
    val deleteHighlight: MutableLiveData<Unit> by lazy { MutableLiveData<Unit>() }

    fun getAllHighlights(frg: HighlightsFragment) {
        dbRepository.getAllHighlights(
            { data->
                data.observe(frg, Observer {
                    highlightsData.value = it
                })
            },
            {

            }
        ).also { compositeDisposable.dispose() }
    }
    fun deleteHighlightById(id: Int) {
        dbRepository.deleteHighlight(
            id,
            {
                deleteHighlight.value = it
            },
            {

            }
        ).also { compositeDisposable.dispose() }
    }
}
