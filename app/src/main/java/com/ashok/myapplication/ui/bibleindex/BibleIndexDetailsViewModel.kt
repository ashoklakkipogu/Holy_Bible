package com.ashok.myapplication.ui.bibleindex

import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashok.myapplication.data.local.repositary.DbRepository
import com.ashok.myapplication.ui.utilities.SharedPrefUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BibleIndexDetailsViewModel @Inject constructor(
    val dbRepo: DbRepository,
    val pref: SharedPreferences
) : ViewModel() {


    var verseList by mutableStateOf<List<Int>>(listOf())
        private set



    fun onEvent(event: BibleIndexDetailsEvent) {
        when (event) {
            is BibleIndexDetailsEvent.VerseByBookIdAndLangauge -> {
                getBibleVerseByBookIdAndChapterId(event.bookId, event.chapterID)
            }
        }
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
