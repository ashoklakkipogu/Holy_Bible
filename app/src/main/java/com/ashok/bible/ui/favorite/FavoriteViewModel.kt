package com.ashok.bible.ui.favorite

import androidx.lifecycle.MutableLiveData
import com.ashok.bible.data.local.repositary.DbRepository
import com.ashok.bible.data.remote.network.ApiError
import com.ashok.bible.ui.base.BaseViewModel
import com.ashok.bible.ui.model.FavBookMark
import com.ashok.bible.data.local.dao.HighlightDao
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(
    private val dbRepository: DbRepository,
    private val highlightDao: HighlightDao

) :
    BaseViewModel() {

    val error: MutableLiveData<ApiError> by lazy { MutableLiveData<ApiError>() }
    val favData: MutableLiveData<List<FavBookMark>> by lazy { MutableLiveData<List<FavBookMark>>() }
    val deleteFavorite: MutableLiveData<Unit> by lazy { MutableLiveData<Unit>() }
    val deleteHighlight: MutableLiveData<Unit> by lazy { MutableLiveData<Unit>() }

    fun getAllFav(frg: FavoriteFragment) {


        dbRepository.getAllFavHigh(
            { data->
                favData.value = data
            },
            {

            }
        ).also { compositeDisposable.dispose() }
    }
    fun deleteFavoriteById(id: Int) {
        dbRepository.deleteFavorite(
            id,
            {
                deleteFavorite.value = it
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
