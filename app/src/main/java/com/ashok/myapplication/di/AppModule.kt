package com.ashok.myapplication.di

import com.ashok.myapplication.data.AppConstants
import com.ashok.myapplication.data.AppConstants.CONNECT_TIMEOUT
import com.ashok.myapplication.data.AppConstants.READ_TIMEOUT
import com.ashok.myapplication.data.AppConstants.WRITE_TIMEOUT
import com.ashok.myapplication.data.api.ApiService
import com.ashok.myapplication.data.datasource.DataSource
import com.ashok.myapplication.data.datasource.DataSourceImpl
import com.ashok.myapplication.data.datasource.UserDataSource
import com.ashok.myapplication.data.datasource.UserDataSourceImpl
import com.ashok.myapplication.ui.repository.LyricRepository
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
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
        okHttpClient.readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
        okHttpClient.writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
        okHttpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        return okHttpClient.build()
    }


    @Singleton
    @Provides
    fun provideProductsApi(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }


    @Singleton
    @Provides
    fun provideProductDataSource(apiService: ApiService): DataSource{
        return DataSourceImpl(apiService)
    }

    @Singleton
    @Provides
    fun provideLyricRepository(dateSource: DataSource): LyricRepository{
        return LyricRepository(dateSource)
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