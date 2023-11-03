package com.ashok.myapplication.data.api

import com.ashok.myapplication.data.entity.Products
import com.ashok.myapplication.data.entity.Users
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("products")
    suspend fun geAllProducts():Response<Products>


    @GET("users")
    suspend fun getUserList(@Query("page") page:Int):Response<Users>
}