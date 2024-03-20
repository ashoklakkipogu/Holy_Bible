package com.ashok.bible.ui.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashok.bible.R
import com.ashok.bible.data.local.entity.BibleJson
import com.ashok.bible.data.local.entity.UserModel
import com.ashok.bible.data.local.entry.BibleIndexModelEntry
import com.ashok.bible.data.local.entry.BibleModelEntry
import com.ashok.bible.data.repository.DbRepoImp
import com.ashok.bible.domain.RequestState
import com.ashok.bible.domain.repository.BibleRepository
import com.ashok.bible.ui.onboarding.OnboardingUIEvent
import com.ashok.bible.ui.onboarding.OnboardingUIState
import com.ashok.bible.ui.utilities.BibleUtils
import com.ashok.bible.ui.utilities.BibleUtils.getBibleIndex
import com.ashok.bible.ui.utilities.BibleUtils.getJsonDataFromUrl
import com.ashok.bible.ui.utilities.BibleUtils.isNetworkAvailable
import com.ashok.bible.ui.utilities.Result
import com.ashok.bible.ui.utilities.SharedPrefUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    val repository: BibleRepository,
    val dbRepo: DbRepoImp,
    val pref: SharedPreferences,
    val application: Application
) :
    ViewModel() {
    var state by mutableStateOf(OnboardingUIState())
    /*private val _bibleInsert = MutableLiveData<Result<Boolean>>(Result.Loading())
    val bibleInsert: LiveData<Result<Boolean>> get() = _bibleInsert*/

    init {
        getUserName()
    }

    fun onEvent(event: OnboardingUIEvent) {
        when (event) {
            is OnboardingUIEvent.InsertBible -> {
                if (isNetworkAvailable(application)) {
                    insertBible(langauge = event.langauge)
                } else {
                    Toast.makeText(
                        application,
                        application.getString(R.string.no_internet),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            OnboardingUIEvent.OnEventIsFirstTime -> {
                //isFirstTime()
            }
        }
    }

    private fun insertUserBible() {

    }

    private fun insertBible(langauge: String) {
        state = state.copy(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            dbRepo.getLanguage(langauge).collect { result ->
                when (result) {
                    is Result.Error -> Unit
                    is Result.Loading -> Unit
                    is Result.Success -> {
                        val prefUserName = SharedPrefUtils.getUserName(pref)
                        if (prefUserName != null) {
                            saveUser(prefUserName, langauge)
                        }
                        //Log.i("data", "data........." + result.data?.size)
                        SharedPrefUtils.setLanguage(pref, langauge)
                        SharedPrefUtils.saveFirstTime(pref)
                        state = if (result.data.isNullOrEmpty()) {
                            val bibleIndex = getBibleIndex(langauge)
                            dbRepo.insertBibleIndex(bibleIndex)
                            val bible = getBibleData(langauge, bibleIndex)
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
        langauge: String,
        bibleIndex: ArrayList<BibleIndexModelEntry>,
    ): ArrayList<BibleModelEntry> {
        //Bible
        val url =
            "https://firebasestorage.googleapis.com/v0/b/bible-8bdba.appspot.com/o/bibledb%2F$langauge%2Fbible.json?alt=media&token=214d396c-ffeb-4cd0-b4a1-bcc852a6f047"
        val jsonFileString = getJsonDataFromUrl(url)
        //Log.i("data", jsonFileString!!)

        val gson = Gson()
        val listBibleJsonType = object : TypeToken<BibleJson>() {}.type
        val bibleJson: BibleJson = gson.fromJson(jsonFileString, listBibleJsonType)
        val booksList = arrayListOf<BibleModelEntry>()
        var inc = 0
        bibleJson.book.forEach { books ->
            books.chapter.forEach { chapter ->
                chapter.verse.forEachIndexed { _, verse ->
                    inc++
                    val first = verse.verseid.subSequence(0, 2).toString().toInt() + 1
                    val second = verse.verseid.subSequence(2, 5).toString().toInt() + 1
                    val third = verse.verseid.subSequence(5, 8).toString().toInt() + 1
                    //println("${verse.verseid}: $first, $second, $third")
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

    fun isFirstTime() = SharedPrefUtils.isFirstTime(pref)
    private fun getUserName() {
        val userName = SharedPrefUtils.getUserName(pref)
        state = state.copy(userName = userName)
    }

    fun saveUserName(userName: String) {
        SharedPrefUtils.setUserName(pref, userName)
    }

    private fun saveUser(name: String, language: String) {
        val obj = UserModel()
        obj.createdDate = BibleUtils.getCurrentTime()
        obj.userName = name
        obj.language = language
        viewModelScope.launch {
            repository.saveUsers(obj).collect { result ->
                when (result) {
                    is RequestState.Success -> {
                        SharedPrefUtils.setUserName(pref, name)
                    }

                    else -> {}
                }
            }
        }
    }

}