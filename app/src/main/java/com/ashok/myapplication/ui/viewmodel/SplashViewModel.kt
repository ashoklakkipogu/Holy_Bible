package com.ashok.myapplication.ui.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashok.myapplication.data.local.entity.BibleJson
import com.ashok.myapplication.data.local.entry.BibleIndexModelEntry
import com.ashok.myapplication.data.local.entry.BibleModelEntry
import com.ashok.myapplication.data.repository.DbRepoImp
import com.ashok.myapplication.ui.dashboard.DashboardUiState
import com.ashok.myapplication.ui.onboarding.OnboardingUIEvent
import com.ashok.myapplication.ui.onboarding.OnboardingUIState
import com.ashok.myapplication.ui.utilities.BibleUtils.getBibleIndex
import com.ashok.myapplication.ui.utilities.BibleUtils.getJsonDataFromUrl
import com.ashok.myapplication.ui.utilities.Result
import com.ashok.myapplication.ui.utilities.SharedPrefUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(val dbRepo: DbRepoImp, val application: Application) :
    ViewModel() {

    @Inject
    lateinit var pref: SharedPreferences

    var state by mutableStateOf(OnboardingUIState())
    /*private val _bibleInsert = MutableLiveData<Result<Boolean>>(Result.Loading())
    val bibleInsert: LiveData<Result<Boolean>> get() = _bibleInsert*/

    fun onEvent(event: OnboardingUIEvent) {
        when (event) {
            is OnboardingUIEvent.InsertBible -> {
                insertBible(langauge = event.langauge)
            }

            OnboardingUIEvent.OnEventIsFirstTime -> {
                isFirstTime()
            }
        }
    }

    private fun insertBible(langauge: String) {
        state = state.copy(isLoading = true)

        viewModelScope.launch {
            dbRepo.getLanguage().collect { result ->
                when (result) {
                    is Result.Error -> Unit
                    is Result.Loading -> Unit
                    is Result.Success -> {
                        state = if (result.data?.isEmpty() == true) {
                            SharedPrefUtils.setLanguage(pref, langauge)
                            SharedPrefUtils.saveFirstTime(pref)
                            val bibleIndex = getBibleIndex(application, langauge)
                            dbRepo.insertBibleIndex(bibleIndex)
                            val bible = getBibleData(application, langauge, bibleIndex)
                            dbRepo.insertBible(bible)
                            state.copy(isLoading = false, isBibleInserted = true)
                        } else {
                            state.copy(isLoading = false, isBibleInserted = true)
                        }
                    }
                }
            }

        }
    }

    private suspend fun getBibleData(
        context: Context,
        langauge: String,
        bibleIndex: ArrayList<BibleIndexModelEntry>,
    ): ArrayList<BibleModelEntry> {
        //Bible
        val url =
            "https://firebasestorage.googleapis.com/v0/b/bible-8bdba.appspot.com/o/bibledb%2F$langauge%2Fbible.json?alt=media&token=214d396c-ffeb-4cd0-b4a1-bcc852a6f047"
        val jsonFileString = getJsonDataFromUrl(url)
        Log.i("data", jsonFileString!!)

        val gson = Gson()
        val listBibleJsonType = object : TypeToken<BibleJson>() {}.type
        val bibleJson: BibleJson = gson.fromJson(jsonFileString, listBibleJsonType)
        val booksList = arrayListOf<BibleModelEntry>()
        var inc = 0
        bibleJson.book.forEach { books ->
            books.chapter.forEach { chapter ->
                chapter.verse.forEachIndexed { i, verse ->
                    inc++
                    val first = verse.verseid.subSequence(0, 2).toString().toInt() + 1
                    val second = verse.verseid.subSequence(2, 5).toString().toInt() + 1
                    val third = verse.verseid.subSequence(5, 8).toString().toInt() + 1
                    println("${verse.verseid}: $first, $second, $third")
                    val book = BibleModelEntry()
                    book.Book = first
                    book.Chapter = second
                    book.Versecount = third
                    book.verse = verse.Verse
                    book.langauge = langauge
                    book.bibleID = "$langauge-${verse.verseid}"
                    book.bibleLangIndex = "$langauge-$inc"
                    book.bibleIndex = bibleIndex[first - 1].chapter
                    booksList.add(book)

                }
            }
        }
        return booksList
    }

    fun isFirstTime() {
        state = state.copy(isFirstTime = SharedPrefUtils.isFirstTime(pref))
    }


}