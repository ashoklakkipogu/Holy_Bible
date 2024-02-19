package com.ashok.myapplication.ui.viewmodel

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ashok.myapplication.data.entity.FavBookMark
import com.ashok.myapplication.data.entity.Products
import com.ashok.myapplication.data.local.entry.BibleModelEntry
import com.ashok.myapplication.data.local.entry.FavoriteModelEntry
import com.ashok.myapplication.data.local.entry.HighlightModelEntry
import com.ashok.myapplication.data.local.entry.NoteModelEntry
import com.ashok.myapplication.data.local.model.FavModel
import com.ashok.myapplication.data.local.model.NoteModel
import com.ashok.myapplication.data.local.repositary.DbRepository
import com.ashok.myapplication.domain.GetBiblePagedUseCase
import com.ashok.myapplication.ui.repository.ProductRepository
import com.ashok.myapplication.ui.utilities.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import com.ashok.myapplication.ui.utilities.SharedPrefUtils
import com.ashok.myapplication.ui.utilities.favDelete

@HiltViewModel
class HomeViewModel @Inject constructor(
    val productRepository: ProductRepository,
    val dbRepo: DbRepository,
    val pref: SharedPreferences
) :
    ViewModel() {

    val useCase = GetBiblePagedUseCase(dbRepo)

    //stateflow
    private val _products: MutableStateFlow<Result<Products>> = MutableStateFlow(Result.Loading())
    val products: StateFlow<Result<Products>> = _products


    //Livedata
    private val _products1: MutableStateFlow<Result<Products>> = MutableStateFlow(Result.Loading())
    val products1: StateFlow<Result<Products>> = _products1


    private val _bibleData = MutableLiveData<Result<List<BibleModelEntry>>>()
    val bibleData: LiveData<Result<List<BibleModelEntry>>> get() = _bibleData

    var bibleScrollPos by mutableIntStateOf(0)
        private set


    /*private val _bibleScrollPos = mutableIntStateOf(0)
    val bibleScrollPos: State<Int> = _bibleScrollPos*/
    fun getAllProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.getAllProducts().collectLatest {
                _products.value = it
            }
        }
    }

    /*fun getBibleData() {
        viewModelScope.launch(Dispatchers.IO) {
            val bibleData = dbRepo.getBible()
            val highlightsData = dbRepo.getAllHighlights()
            val favData = dbRepo.getAllFav()
            val noteData = dbRepo.getAllNotes()



            bibleData?.forEach {
                it.apply {
                    highlightsData?.forEach { highlightsData ->
                        if (id == highlightsData.bibleId) {
                            selectedBackground = highlightsData.colorCode
                        }
                    }
                    favData?.forEach { fav ->
                        if (id == fav.bibleId) {
                            isBookMark = true
                        }
                    }
                    noteData?.forEach { note ->
                        if (id == note.bibleId) {
                            isNote = true
                        }
                    }
                }
            }

            Log.i("data", "data1........$bibleData")
            withContext(Dispatchers.Main) {
                *//* allModel.bible = bibleData!!
                 allModel.notes = noteData!!*//*
                _bibleData.value = Result.Success(bibleData!!)
            }

        }
    }
*/
    val biblePagingSource: Pager<Int, BibleModelEntry> = useCase.call()
    /*.cachedIn(viewModelScope)*/

    fun getBibleListByBookIdAndChapterId(
        bookId: Int,
        chapterId: Int,
    ) {
        val language = SharedPrefUtils.getLanguage(pref)!!
        dbRepo.getBibleListByBookIdAndChapterId(bookId, chapterId, language)
    }


    fun getBibleScrollPosition(
        clickAction: String,
        bookId: Int,
        chapterId: Int,
        verseId: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            var data = dbRepo.getBibleScrollPosition(bookId, chapterId, verseId)
            if (data == null) {
                println("$bookId, $chapterId")
                if (clickAction == "LEFT") {
                    val lastBiblePosItem = dbRepo.getBibleScrollLastPosition(bookId - 1)
                    if (lastBiblePosItem != null)
                        data =
                            dbRepo.getBibleScrollPosition(
                                lastBiblePosItem.Book,
                                lastBiblePosItem.Chapter,
                                1
                            )
                } else if (clickAction == "RIGHT") {
                    data = dbRepo.getBibleScrollPosition(bookId + 1, 1, 1)
                }
            }

            withContext(Dispatchers.Main) {
                data?.let {
                    Log.i(
                        "data....",
                        "data............." + (it.bibleLangIndex.split("-")[1].toInt() - 1)
                    )
                    bibleScrollPos = it.bibleLangIndex.split("-")[1].toInt() - 1
                }
            }


        }
    }


    fun noteInsert(model: NoteModelEntry) {
        viewModelScope.launch(Dispatchers.IO) {
            dbRepo.deleteNotesByBibleLangIndex(model.bibleLangIndex)
            dbRepo.insertAllNotes(arrayListOf(model))
        }
    }

    fun bookmarkInsert(model: FavoriteModelEntry) {
        viewModelScope.launch(Dispatchers.IO) {
            dbRepo.deleteFavoriteByBibleLangIndex(model.bibleLangIndex)
            dbRepo.insertAllFav(arrayListOf(model))
        }
    }

    fun highlightInsert(model: HighlightModelEntry) {
        viewModelScope.launch(Dispatchers.IO) {
            dbRepo.deleteHighlightByBibleLangIndex(model.bibleLangIndex)
            dbRepo.insertAllHighlight(arrayListOf(model))
        }
    }

    fun highlightDeleteByBibleId(bibleLangIndex: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dbRepo.deleteHighlightByBibleLangIndex(bibleLangIndex)
        }
    }

    fun deleteFavById(bibleLangIndex: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dbRepo.deleteFavoriteByBibleLangIndex(bibleLangIndex)
        }
    }

    fun setOrResetBibleScrollPos(pos: Int) {
        bibleScrollPos = pos
    }


}
