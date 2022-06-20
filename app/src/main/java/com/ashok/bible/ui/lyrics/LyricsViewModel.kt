package com.ashok.bible.ui.lyrics

import androidx.lifecycle.MutableLiveData
import com.ashok.bible.data.local.dao.LyricsDao
import com.ashok.bible.data.local.repositary.DbRepository
import com.ashok.bible.data.remote.model.BaseModel
import com.ashok.bible.data.remote.model.LyricsModel
import com.ashok.bible.data.remote.network.ApiError
import com.ashok.bible.data.remote.repositary.AppRepoImp
import com.ashok.bible.ui.base.BaseViewModel
import javax.inject.Inject

class LyricsViewModel @Inject constructor(
    private val dbRepository: DbRepository,
    private val appRepository: AppRepoImp,
    private val lyricsDao: LyricsDao
) :
    BaseViewModel() {


    val error: MutableLiveData<ApiError> by lazy { MutableLiveData<ApiError>() }
    val isNoLyric: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val createLyric: MutableLiveData<BaseModel> by lazy { MutableLiveData<BaseModel>() }

   /* fun getLyrics() {
        appRepository.getLyrics(
            {
                val distinctLocations = it.distinctBy { it1 -> it1.titleEn }
                lyricData.value = distinctLocations
            },
            {
                error.value = it
            }
        ).also { compositeDisposable.add(it) }
    }*/

    fun createLyric(obj: LyricsModel) {
        appRepository.createLyric(obj, {
            createLyric.value = it

        }, {
            error.value = it
        }).also { compositeDisposable.add(it) }
    }
    fun insertAllLyrics(isNullOrEmpty: Boolean) {
        appRepository.insertLyricsForLocal(
            {
                if (it || isNullOrEmpty){
                    isNoLyric.value = it
                }
            },
            {
                error.value = it
            }
        ).also { compositeDisposable.add(it) }
    }
    fun getAllLyricsLocal():List<LyricsModel> {
        return lyricsDao.getAllLyricsLimit()
    }
    fun getAllLyricsByLimit(offset:Int): List<LyricsModel>{
        val limit =20
        return lyricsDao.getAllLyricsByLimit(limit, offset)
    }
    fun getSongsByTitle(offset:Int, search:String): List<LyricsModel>{
        val limit =20
        val s = "%$search%"
        return lyricsDao.getSongsByTitle(limit, offset, s)
    }

    fun getSongsByTitleEn(offset:Int, search:String): List<LyricsModel>{
        val limit =20
        val s = "%$search%"
        return lyricsDao.getSongsByTitleEn(limit, offset, s)
    }
}