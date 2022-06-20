package com.ashok.bible.data.remote.repositary

import com.ashok.bible.data.local.dao.LyricsDao
import com.ashok.bible.data.remote.ApiService
import com.ashok.bible.data.remote.NotificationMessagingService
import com.ashok.bible.data.remote.model.*
import com.ashok.bible.data.remote.network.ApiDisposable
import com.ashok.bible.data.remote.network.ApiError
import com.ashok.bible.ui.model.NotificationMsgModel
import com.ashok.bible.utils.MappedFireBaseData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AppRepoImp @Inject constructor(
    val apiService: ApiService,
    val notificationService: NotificationMessagingService,
    val lyricsDao: LyricsDao
) :
    AppRepository {
    override fun getCarousel(
        success: (List<CarouselModel>) -> Unit,
        failure: (ApiError) -> Unit,
        terminate: () -> Unit
    ): Disposable {
        return apiService
            .getCarousel()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnTerminate(terminate)
            .subscribeWith(
                ApiDisposable<List<CarouselModel>>(
                    {
                        success(it)
                    },
                    failure
                )
            )
    }

    override fun getLyrics(
        success: (List<LyricsModel>) -> Unit,
        failure: (ApiError) -> Unit,
        terminate: () -> Unit
    ): Disposable {
        return apiService
            .getLyrics()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnTerminate(terminate)
            .subscribeWith(
                ApiDisposable<Map<String, LyricsModel>>(
                    {
                        val data = ArrayList<LyricsModel>()
                        val map = it
                        for ((key, value) in map) {
                            val sample: LyricsModel = value
                            sample.lyricId = key
                            data.add(sample)
                        }
                        //val data = MappedFireBaseData<LyricsModel>().getFireBaseMapData(it)
                        success(data)
                    },
                    failure
                )
            )
    }

    override fun insertLyricsForLocal(
        success: (Boolean) -> Unit,
        failure: (ApiError) -> Unit,
        terminate: () -> Unit
    ): Disposable {
        return apiService
            .getLyrics()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnTerminate(terminate)
            .subscribeWith(
                ApiDisposable<Map<String, LyricsModel>>(
                    {
                        GlobalScope.launch(Dispatchers.Main) {
                            // Switch to a background (IO) thread
                            val isNoData = withContext(Dispatchers.IO) {
                                var isNoData = true
                                for ((key, value) in it) {
                                    val sample: LyricsModel = value
                                    val lyric = lyricsDao.getSongsById(key, value.content)
                                    if (lyric!=null && lyric.lyricId.isNotEmpty()) {
                                        isNoData = false
                                    }else{
                                        sample.lyricId = key
                                        lyricsDao.insertLyrics(sample)
                                    }
                                }
                                isNoData
                            }
                            success(isNoData)
                        }


                    },
                    failure
                )
            )
    }

    override fun sendNotificationMsg(
        notificationMsgModel: NotificationMsgModel,
        success: (BaseModel) -> Unit,
        failure: (ApiError) -> Unit,
        terminate: () -> Unit
    ): Disposable {
        return notificationService
            .sendMessage(notificationMsgModel)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnTerminate(terminate)
            .subscribeWith(
                ApiDisposable<BaseModel>(
                    {
                        success(it)
                    },
                    failure
                )
            )
    }

    override fun createLyric(
        obj: LyricsModel,
        success: (BaseModel) -> Unit,
        failure: (ApiError) -> Unit,
        terminate: () -> Unit
    ): Disposable {
        return apiService
            .createLyric(obj)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnTerminate(terminate)
            .subscribeWith(
                ApiDisposable<BaseModel>(
                    {
                        success(it)
                    },
                    failure
                )
            )
    }

    override fun getBannerModel(
        success: (List<BannerModel>) -> Unit,
        failure: (ApiError) -> Unit,
        terminate: () -> Unit
    ): Disposable {
        return apiService
            .getBannerModel()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnTerminate(terminate)
            .subscribeWith(
                ApiDisposable<Map<String, BannerModel>>(
                    {
                        val data = MappedFireBaseData<BannerModel>().getFireBaseMapData(it)
                        success(data)
                    },
                    failure
                )
            )
    }

    override fun getQuotes(
        lang: String,
        success: (Map<String, List<QuotesModel>>) -> Unit,
        failure: (ApiError) -> Unit,
        terminate: () -> Unit
    ): Disposable {
        return apiService
            .getQuotes(lang)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnTerminate(terminate)
            .subscribeWith(
                ApiDisposable<Map<String, List<QuotesModel>>>(
                    {
                        //val data = MappedFireBaseData<QuotesModel>().getFireBaseMapData(it)
                        success(it)
                    },
                    failure
                )
            )
    }

    override fun createQuotes(
        obj: QuotesModel,
        success: (BaseModel) -> Unit,
        failure: (ApiError) -> Unit,
        terminate: () -> Unit
    ): Disposable {
        return apiService
            .createQuotes(obj)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnTerminate(terminate)
            .subscribeWith(
                ApiDisposable<BaseModel>(
                    {
                        success(it)
                    },
                    failure
                )
            )
    }

    override fun createBanner(
        obj: BannerModel,
        success: (BaseModel) -> Unit,
        failure: (ApiError) -> Unit,
        terminate: () -> Unit
    ): Disposable {
        return apiService
            .createBanner(obj)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnTerminate(terminate)
            .subscribeWith(
                ApiDisposable<BaseModel>(
                    {
                        success(it)
                    },
                    failure
                )
            )
    }

    override fun saveUser(
        obj: UserModel,
        success: (BaseModel) -> Unit,
        failure: (ApiError) -> Unit,
        terminate: () -> Unit
    ): Disposable {
        return apiService
            .saveUsers(obj)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnTerminate(terminate)
            .subscribeWith(
                ApiDisposable<BaseModel>(
                    {
                        success(it)
                    },
                    failure
                )
            )
    }
}