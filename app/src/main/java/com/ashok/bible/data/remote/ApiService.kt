package com.ashok.bible.data.remote

import io.reactivex.Observable
import retrofit2.http.*
import java.util.*
import retrofit2.http.POST
import android.R.attr.data
import com.ashok.bible.data.remote.model.*
import com.ashok.bible.ui.model.NotificationMsgModel
import okhttp3.MultipartBody


interface ApiService {
    @GET("carousel")
    fun getCarousel(): Observable<List<CarouselModel>>

    @GET("lyrics.json")
    fun getLyrics(): Observable<Map<String, LyricsModel>>

    @POST("lyrics.json")
    fun createLyric(@Body lyricsModel: LyricsModel): Observable<BaseModel>

    @GET("banners.json")
    fun getBannerModel(): Observable<Map<String, BannerModel>>

    @GET("quotes/{lang}.json")
    fun getQuotes(@Path("lang") lang:String): Observable<Map<String, List<QuotesModel>>>

    @POST("quotes.json")
    fun createQuotes(@Body quotesModel: QuotesModel): Observable<BaseModel>

    @POST("banners.json")
    fun createBanner(@Body bannerModel: BannerModel): Observable<BaseModel>

    @POST("users.json")
    fun saveUsers(@Body userModel: UserModel): Observable<BaseModel>
}