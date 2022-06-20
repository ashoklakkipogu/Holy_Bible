package com.ashok.bible.data.remote.repositary

import com.ashok.bible.data.remote.model.*
import io.reactivex.disposables.Disposable
import com.ashok.bible.data.remote.network.ApiError
import com.ashok.bible.ui.model.NotificationMsgModel
import okhttp3.MultipartBody

interface AppRepository {
    fun getCarousel(
        success: (List<CarouselModel>) -> Unit,
        failure: (ApiError) -> Unit = {},
        terminate: () -> Unit = {}
    ): Disposable

    fun getLyrics(
        success: (List<LyricsModel>) -> Unit,
        failure: (ApiError) -> Unit = {},
        terminate: () -> Unit = {}
    ): Disposable

    fun insertLyricsForLocal(
        success: (Boolean) -> Unit,
        failure: (ApiError) -> Unit = {},
        terminate: () -> Unit = {}
    ): Disposable
    fun sendNotificationMsg(
        notificationMsgModel: NotificationMsgModel,
        success: (BaseModel) -> Unit,
        failure: (ApiError) -> Unit = {},
        terminate: () -> Unit = {}
    ): Disposable
    fun createLyric(
        obj: LyricsModel,
        success: (BaseModel) -> Unit,
        failure: (ApiError) -> Unit = {},
        terminate: () -> Unit = {}
    ): Disposable

    fun getBannerModel(
            success: (List<BannerModel>) -> Unit,
            failure: (ApiError) -> Unit = {},
            terminate: () -> Unit = {}
    ): Disposable

    fun getQuotes(
            lang:String,
            success: (Map<String, List<QuotesModel>>) -> Unit,
            failure: (ApiError) -> Unit = {},
            terminate: () -> Unit = {}
    ): Disposable


    fun createQuotes(
            obj: QuotesModel,
            success: (BaseModel) -> Unit,
            failure: (ApiError) -> Unit = {},
            terminate: () -> Unit = {}
    ): Disposable

    fun createBanner(
            obj: BannerModel,
            success: (BaseModel) -> Unit,
            failure: (ApiError) -> Unit = {},
            terminate: () -> Unit = {}
    ): Disposable

    fun saveUser(
            obj: UserModel,
            success: (BaseModel) -> Unit,
            failure: (ApiError) -> Unit = {},
            terminate: () -> Unit = {}
    ): Disposable



}