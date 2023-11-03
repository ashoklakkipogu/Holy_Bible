package com.ashok.myapplication.data.datasource

import com.ashok.myapplication.data.entity.Products
import retrofit2.Response

interface ProductDataSource {
    suspend fun geAllProducts():Response<Products>
}