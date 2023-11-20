package com.ashok.myapplication.ui.viewmodel

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashok.myapplication.data.entity.BibleJson
import com.ashok.myapplication.data.entity.Products
import com.ashok.myapplication.data.entity.Users
import com.ashok.myapplication.data.local.entry.BibleIndexModelEntry
import com.ashok.myapplication.data.local.entry.BibleModelEntry
import com.ashok.myapplication.data.local.repositary.DbRepoImp
import com.ashok.myapplication.ui.utilities.BibleUtils.getBibleIndex
import com.ashok.myapplication.ui.utilities.BibleUtils.getJsonDataFromAsset
import com.ashok.myapplication.ui.utilities.Result
import com.ashok.myapplication.ui.utilities.SharedPrefUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(val dbRepo: DbRepoImp) :
    ViewModel() {

    @Inject
    lateinit var pref: SharedPreferences

    private val _bibleInsert = MutableLiveData<Result<Boolean>>(Result.Loading())
    val bibleInsert: LiveData<Result<Boolean>> get() = _bibleInsert

    fun insertBible(context: Context, langauge: String) {
        SharedPrefUtils.setLanguage(pref, langauge)
        SharedPrefUtils.saveFirstTime(pref)
        viewModelScope.launch(Dispatchers.IO) {
            val bibleIndex = getBibleIndex(context, langauge)
            dbRepo.insertBibleIndex(bibleIndex)
            val bible = getBibleData(context, langauge, bibleIndex)
            dbRepo.insertBible(bible)
            _bibleInsert.postValue(Result.Success(true))
        }
    }

    private suspend fun getBibleData(
        context: Context,
        langauge: String,
        bibleIndex: ArrayList<BibleIndexModelEntry>,
    ): ArrayList<BibleModelEntry> {
        //Bible
        val jsonFileString = getJsonDataFromAsset(context, "bible.json")
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

    fun isFirstTime() = SharedPrefUtils.isFirstTime(pref)


}