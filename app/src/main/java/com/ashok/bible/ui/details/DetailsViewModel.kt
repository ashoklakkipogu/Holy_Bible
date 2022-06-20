package com.ashok.bible.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.ashok.bible.data.local.entry.BibleIndexModelEntry
import com.ashok.bible.data.local.entry.FavoriteModelEntry
import com.ashok.bible.data.local.entry.HighlightModelEntry
import com.ashok.bible.data.local.entry.NoteModelEntry
import com.ashok.bible.data.local.repositary.DbRepository
import com.ashok.bible.data.remote.model.BannerModel
import com.ashok.bible.data.remote.network.ApiError
import com.ashok.bible.data.remote.repositary.AppRepoImp
import com.ashok.bible.ui.base.BaseViewModel
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val dbRepository: DbRepository,
    private val appRepository: AppRepoImp
) :
    BaseViewModel() {

    val error: MutableLiveData<ApiError> by lazy { MutableLiveData<ApiError>() }
    val bibleIndexData: MutableLiveData<List<BibleIndexModelEntry>> by lazy { MutableLiveData<List<BibleIndexModelEntry>>() }
    val insertFav: MutableLiveData<Unit> by lazy { MutableLiveData<Unit>() }
    val insertNotes: MutableLiveData<Unit> by lazy { MutableLiveData<Unit>() }
    val favData: MutableLiveData<FavoriteModelEntry> by lazy { MutableLiveData<FavoriteModelEntry>() }
    val notesData: MutableLiveData<NoteModelEntry> by lazy { MutableLiveData<NoteModelEntry>() }
    val highlightsData: MutableLiveData<HighlightModelEntry> by lazy { MutableLiveData<HighlightModelEntry>() }
    val insertHighlight: MutableLiveData<Unit> by lazy { MutableLiveData<Unit>() }
    val deleteFavorite: MutableLiveData<Unit> by lazy { MutableLiveData<Unit>() }
    val deleteNote: MutableLiveData<Unit> by lazy { MutableLiveData<Unit>() }
    val deleteHighlight: MutableLiveData<Unit> by lazy { MutableLiveData<Unit>() }

    val bannerData: MutableLiveData<List<BannerModel>> by lazy { MutableLiveData<List<BannerModel>>() }

    fun getBanner() {
        appRepository.getBannerModel(
                {
                    bannerData.value = it
                },
                {
                    error.value = it
                }
        ).also { compositeDisposable.dispose() }
    }

    fun getBibleIndex(activity: DetailsActivity) {
        dbRepository.getBibleIndex(
            { data ->
                data.observe(activity, Observer {
                    bibleIndexData.value = it
                })
            },
            {
                error.value = it
            }
        ).also { compositeDisposable.dispose() }
    }

    fun insertFavorites(favList: ArrayList<FavoriteModelEntry>) {
        dbRepository.insertAllFav(
            favList,
            {
                insertFav.value = it
            },
            {
                error.value = it
            }
        ).also { compositeDisposable.dispose() }
    }

    fun getFavById(frg: DetailsActivity, id:Int) {
        dbRepository.getFavById(
            id,
            { data ->
                data.observe(frg, Observer {
                    favData.value = it
                })
            },
            {

            }
        ).also { compositeDisposable.dispose() }
    }

    fun insertNotes(noteList: ArrayList<NoteModelEntry>) {
        dbRepository.insertAllNotes(
            noteList,
            {
                insertNotes.value = it
            },
            {
                error.value = it
            }
        ).also { compositeDisposable.dispose() }
    }

    fun getNotesById(frg: DetailsActivity, id:Int) {
        dbRepository.getNotesById(
            id,
            { data ->
                data.observe(frg, Observer {
                    notesData.value = it
                })
            },
            {

            }
        ).also { compositeDisposable.dispose() }
    }

    fun insertHighlights(highlight: ArrayList<HighlightModelEntry>) {
        dbRepository.insertAllHighlight(
            highlight,
            {
                insertHighlight.value = it
            },
            {
                error.value = it
            }
        ).also { compositeDisposable.dispose() }
    }

    fun getHighlightById(frg: DetailsActivity, id:Int) {
        dbRepository.getHighlightById(
            id,
            { data ->
                data.observe(frg, Observer {
                    highlightsData.value = it
                })
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

    fun deleteNoteById(id: Int) {
        dbRepository.deleteNote(
            id,
            {
                deleteNote.value = it
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
