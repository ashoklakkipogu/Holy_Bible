package com.ashok.myapplication.ui.repository

import com.ashok.myapplication.data.datasource.ProductDataSource
import com.ashok.myapplication.data.entity.Products
import com.ashok.myapplication.ui.utilities.Result
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class ProductRepository @Inject constructor(private val productDataSource: ProductDataSource) {
    suspend fun getAllProducts():Flow<Result<Products>>{
        return flow {
            val response = productDataSource.geAllProducts()
            emit(Result.Loading())
            if (response.isSuccessful && response.body() !=null){
                emit(Result.Success(response.body()!!))
            }else{
                emit(Result.Error("Error fetching products data"))
            }
        }.catch { e->
            emit(Result.Error(e.localizedMessage?:"Some error in flow"))
        }
    }
}