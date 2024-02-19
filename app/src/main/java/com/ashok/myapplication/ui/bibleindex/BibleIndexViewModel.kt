package com.ashok.myapplication.ui.bibleindex

import android.content.SharedPreferences
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashok.myapplication.data.local.entry.BibleIndexModelEntry
import com.ashok.myapplication.data.local.model.NoteModel
import com.ashok.myapplication.data.local.repositary.DbRepository
import com.ashok.myapplication.ui.repository.ProductRepository
import com.ashok.myapplication.ui.utilities.SharedPrefUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BibleIndexViewModel @Inject constructor(
    val dbRepo: DbRepository,
    val pref: SharedPreferences
) : ViewModel() {

    /* val bibileIndex = viewModelScope.launch {
         dbRepo.getBibleIndex()
     }*/
    var bibileIndex by mutableStateOf<List<BibleIndexModelEntry>>(listOf())
        private set

    var chaptersList by mutableStateOf<List<Int>>(listOf())
        private set

    var verseList by mutableStateOf<List<Int>>(listOf())
        private set

    init {
        getBibleIndex()
    }

    fun getBibleIndex() = viewModelScope.launch(Dispatchers.IO) {
        val result = dbRepo.getBibleIndex()
        withContext(Dispatchers.Main) {
            bibileIndex = result
        }
    }

    fun onEvent(event: BibleIndexEvent) {
        when (event) {
            is BibleIndexEvent.ChaptersByBookIdAndLangauge -> {
                getBibleChaptersByBookIdAndLangauge(event.id)
            }

            is BibleIndexEvent.VerseByBookIdAndLangauge -> {
                getBibleVerseByBookIdAndChapterId(event.bookId, event.chapterID)
            }
        }
    }

    private fun getBibleChaptersByBookIdAndLangauge(id: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            val set = mutableSetOf<Int>()
            chaptersList = set.toMutableList()
            val language = SharedPrefUtils.getLanguage(pref)!!
            val result = dbRepo.getBibleChaptersByBookIdAndLangauge(id, language)
            //withContext(Dispatchers.Main) {
                for (obj in result) {
                    set.add(obj.Chapter)
                }
            delay(500)
                chaptersList = set.toMutableList()
            //}
        }

    private fun getBibleVerseByBookIdAndChapterId(
        bookId: Int,
        chapterID: Int,
    ) = viewModelScope.launch(Dispatchers.IO) {
        val language = SharedPrefUtils.getLanguage(pref)!!
        val result = dbRepo.getBibleVerseByBookIdAndChapterId(bookId, chapterID, language)
        withContext(Dispatchers.Main) {
            val set = mutableSetOf<Int>()
            for (obj in result) {
                set.add(obj.Versecount)
            }
            verseList = set.toMutableList()
        }
    }
}
