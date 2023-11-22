package com.ashok.myapplication.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashok.myapplication.data.entity.Products
import com.ashok.myapplication.data.local.entry.BibleModelEntry
import com.ashok.myapplication.data.local.repositary.DbRepository
import com.ashok.myapplication.ui.repository.ProductRepository
import com.ashok.myapplication.ui.utilities.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val productRepository: ProductRepository,
    val dbRepo: DbRepository
) :
    ViewModel() {

    //stateflow
    private val _products: MutableStateFlow<Result<Products>> = MutableStateFlow(Result.Loading())
    val products: StateFlow<Result<Products>> = _products

    //Livedata
    private val _products1: MutableStateFlow<Result<Products>> = MutableStateFlow(Result.Loading())
    val products1: StateFlow<Result<Products>> = _products1


    private val _bibleData = MutableLiveData<Result<List<BibleModelEntry>>>()
    val bibleData: LiveData<Result<List<BibleModelEntry>>> get() = _bibleData

    private val _bibleScrollPos = MutableLiveData<Int>()
    val bibleScrollPos: LiveData<Int> get() = _bibleScrollPos

    fun getAllProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.getAllProducts().collectLatest {
                _products.value = it
            }
        }
    }

    fun getBibleData() {
        viewModelScope.launch(Dispatchers.IO) {
            val bibleData = dbRepo.getBible()
            var noteData = dbRepo.getAllNotes()


            Log.i("data", "data1........$bibleData")
            withContext(Dispatchers.Main) {
               /* allModel.bible = bibleData!!
                allModel.notes = noteData!!*/
                _bibleData.value = Result.Success(bibleData!!)
            }

        }
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
                    _bibleScrollPos.value = it.bibleLangIndex.split("-")[1].toInt() - 1
                }
            }

        }
    }
}
