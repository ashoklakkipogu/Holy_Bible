package com.ashok.bible.ui.bibleindex

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.ashok.bible.data.local.entry.BibleIndexModelEntry
import com.ashok.bible.data.local.repositary.DbRepository
import com.ashok.bible.data.remote.network.ApiError
import com.ashok.bible.data.remote.repositary.AppRepository
import com.ashok.bible.ui.base.BaseViewModel
import javax.inject.Inject

class BibleIndexViewModel @Inject constructor(
    private val appRepository: AppRepository,
    private val dbRepository: DbRepository
) :
    BaseViewModel() {

    val error: MutableLiveData<ApiError> by lazy { MutableLiveData<ApiError>() }
    val bibleIndexData: MutableLiveData<List<BibleIndexModelEntry>> by lazy { MutableLiveData<List<BibleIndexModelEntry>>() }
    val bibleChapters: MutableLiveData<List<Int>> by lazy { MutableLiveData<List<Int>>() }
    val bibleVerseCount: MutableLiveData<List<Int>> by lazy { MutableLiveData<List<Int>>() }
    fun getBibleIndex(activity: BibleIndexActivity) {
        dbRepository.getBibleIndex(
            { data->
                data.observe(activity, Observer {
                    bibleIndexData.value = it
                })
            },
            {

            }
        ).also { compositeDisposable.dispose() }
    }
    fun getBibleByBookId(activity: BibleIndexActivity, bookId:Int) {
        dbRepository.getBibleByBookId(
            bookId,
            { data->
                data.observe(activity, Observer {
                    val set = mutableSetOf<Int>()
                    for (obj in it){
                        set.add(obj.Chapter)
                    }
                    bibleChapters.value = set.toMutableList()

                })
            },
            {

            }
        ).also { compositeDisposable.dispose() }
    }
    fun getBibleByBookIdAndChapterId(activity: BibleIndexActivity, bookId:Int,chapterID:Int) {
        dbRepository.getBibleByBookIdAndChapterId(
            bookId,
            chapterID,
            { data->
                data.observe(activity, Observer {
                    val set = mutableSetOf<Int>()
                    for (obj in it){
                        set.add(obj.Versecount)
                    }
                    bibleVerseCount.value = set.toList()
                })
            },
            {

            }
        ).also { compositeDisposable.dispose() }
    }
}
