package com.ashok.myapplication.data.remote

import com.ashok.myapplication.data.local.entity.CarouselModel
import com.ashok.myapplication.data.local.entity.LyricsModel
import com.ashok.myapplication.data.local.entity.Products
import com.ashok.myapplication.data.local.entity.QuotesModel
import com.ashok.myapplication.data.local.entity.StatusImagesModel
import com.ashok.myapplication.data.local.entity.StoryModel
import com.ashok.myapplication.data.local.entity.Users
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("lyrics.json")
    suspend fun getLyrics(): Response<Map<String, LyricsModel>>

    @GET("stories.json")
    suspend fun getStories(): Response<Map<String, StoryModel>>

    @GET("products")
    suspend fun geAllProducts():Response<Products>


    @GET("users")
    suspend fun getUserList(@Query("page") page:Int):Response<Users>

    @GET("quotes/{lang}.json")
    suspend fun getQuotes(@Path("lang") lang:String): Response<Map<String, List<QuotesModel>>>

    @GET("statusimages.json")
    suspend fun getStatus(): Response<Map<String, StatusImagesModel>>
}