package com.ashok.myapplication.data.remote

import com.ashok.myapplication.data.local.entity.BaseModel
import com.ashok.myapplication.data.local.entity.CarouselModel
import com.ashok.myapplication.data.local.entity.LyricsModel
import com.ashok.myapplication.data.local.entity.Products
import com.ashok.myapplication.data.local.entity.QuotesModel
import com.ashok.myapplication.data.local.entity.StatusEmptyImagesModel
import com.ashok.myapplication.data.local.entity.StatusImagesModel
import com.ashok.myapplication.data.local.entity.StoryModel
import com.ashok.myapplication.data.local.entity.UserModel
import com.ashok.myapplication.data.local.entity.Users
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {


    @GET("lyrics.json")
    suspend fun getLyrics(@Query("orderBy") orderBy: String = "\"language\"", @Query("equalTo") language: String): Response<Map<String, LyricsModel>>

    @GET("stories.json")
    suspend fun getStories(@Query("orderBy") orderBy: String = "\"language\"", @Query("equalTo") language: String): Response<Map<String, StoryModel>>

    @GET("quotes/{lang}.json")
    suspend fun getQuotes(@Path("lang") lang: String): Response<Map<String, List<QuotesModel>>>

    @GET("statusimages.json")
    suspend fun getStatus(@Query("orderBy") orderBy: String = "\"language\"", @Query("equalTo") language: String): Response<Map<String, StatusImagesModel>>

    @GET("statusemptyImages.json")
    suspend fun getStatusEmptyImages(): Response<Map<String, StatusEmptyImagesModel>>

    @POST("users_v2.json")
    fun saveUsers(@Body userModel: UserModel): Response<BaseModel>

}