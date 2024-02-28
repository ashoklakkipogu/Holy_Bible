package com.ashok.myapplication.ui.bibleindex

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.ashok.myapplication.data.local.repositary.DbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BibleIndexViewModel @Inject constructor(
    val dbRepo: DbRepository,
    val pref: SharedPreferences
) : ViewModel() {




    /*  var chaptersList by mutableStateOf<List<Int>>(listOf())
          private set*/



    fun onEvent(event: BibleIndexEvent) {
      /*  when (event) {
            is BibleIndexEvent.ChaptersByBookIdAndLangauge -> {
                getBibleChaptersByBookIdAndLangauge(event.id)
            }
        }*/
    }

    /*private fun getBibleChaptersByBookIdAndLangauge(id: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            val set = mutableSetOf<Int>()
            chaptersList = set.toMutableList()
            val language = SharedPrefUtils.getLanguage(pref)!!
            val result = dbRepo.getBibleChaptersByBookIdAndLangauge(id, language)
            //withContext(Dispatchers.Main) {
                for (obj in result) {
                    set.add(obj.Chapter)
                }
            //delay(100)
                chaptersList = set.toMutableList()
            //}
        }*/
}
