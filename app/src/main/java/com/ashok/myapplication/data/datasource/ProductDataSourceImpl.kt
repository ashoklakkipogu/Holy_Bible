package com.ashok.myapplication.data.datasource

import com.ashok.myapplication.data.api.ApiService
import com.ashok.myapplication.data.entity.Products
import retrofit2.Response
import javax.inject.Inject

class ProductDataSourceImpl @Inject constructor(val apiService: ApiService): ProductDataSource {
    override suspend fun geAllProducts(): Response<Products> {
        return apiService.geAllProducts()
    }
}