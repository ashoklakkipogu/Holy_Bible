package com.ashok.bible.ui.bibleindex

import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashok.bible.domain.repository.DbRepository
import com.ashok.bible.ui.utilities.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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
    ) = viewModelScope.launch {
        dbRepo.getBibleVerseByBookIdAndChapterId(bookId, chapterID).collect{ result->
            when(result){
                is Result.Error -> Unit
                is Result.Loading -> Unit
                is Result.Success -> {
                    val set = mutableSetOf<Int>()
                    for (obj in result.data!!) {
                        set.add(obj.Versecount)
                    }
                    verseList = set.toMutableList()
                }
            }
        }
    }
}
