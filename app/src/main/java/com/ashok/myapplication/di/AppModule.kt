package com.ashok.myapplication.di

import com.ashok.myapplication.data.api.ApiService
import com.ashok.myapplication.data.datasource.ProductDataSource
import com.ashok.myapplication.data.datasource.ProductDataSourceImpl
import com.ashok.myapplication.data.datasource.UserDataSource
import com.ashok.myapplication.data.datasource.UserDataSourceImpl
import com.ashok.myapplication.ui.repository.ProductRepository
import com.ashok.myapplication.ui.repository.UsersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val httpClient = OkHttpClient().newBuilder().apply {
            addInterceptor(httpLoggingInterceptor)
        }
        httpClient.apply {
            readTimeout(60, TimeUnit.SECONDS)
        }


        return Retrofit.Builder().baseUrl("https://reqres.in/api/")
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideProductsApi(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }


    @Singleton
    @Provides
    fun provideProductDataSource(apiService: ApiService): ProductDataSource{
        return ProductDataSourceImpl(apiService)
    }

    @Singleton
    @Provides
    fun provideProductRepository(productDataSource: ProductDataSource): ProductRepository{
        return ProductRepository(productDataSource)
    }


    @Singleton
    @Provides
    fun provideUserDataSource(apiService: ApiService): UserDataSource{
        return UserDataSourceImpl(apiService)
    }

    @Singleton
    @Provides
    fun provideUserRepository(userDataSource: UserDataSource): UsersRepository{
        return UsersRepository(userDataSource)
    }

}