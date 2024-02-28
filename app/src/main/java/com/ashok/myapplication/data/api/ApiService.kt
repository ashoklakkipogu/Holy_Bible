package com.ashok.myapplication.data.api

import com.ashok.myapplication.data.entity.CarouselModel
import com.ashok.myapplication.data.entity.LyricsModel
import com.ashok.myapplication.data.entity.Products
import com.ashok.myapplication.data.entity.Users
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Observable

interface ApiService {

    @GET("lyrics.json")
    suspend fun getLyrics(): Response<Map<String, LyricsModel>>

    @GET("products")
    suspend fun geAllProducts():Response<Products>


    @GET("users")
    suspend fun getUserList(@Query("page") page:Int):Response<Users>
}