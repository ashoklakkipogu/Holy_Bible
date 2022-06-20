package com.ashok.bible.di.module

import android.app.Application
import com.ashok.bible.data.local.dao.LyricsDao
import com.ashok.bible.data.remote.ApiConstants
import com.ashok.bible.data.remote.ApiService
import com.ashok.bible.data.remote.NotificationMessagingService
import com.ashok.bible.data.remote.repositary.AppRepoImp
import com.google.android.gms.ads.AdRequest
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.ashok.bible.data.remote.repositary.AppRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [SharedPreferencesModule::class, ViewModelModule::class, RoomModule::class])
class AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.connectTimeout(ApiConstants.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
        okHttpClient.readTimeout(ApiConstants.READ_TIMEOUT, TimeUnit.MILLISECONDS)
        okHttpClient.writeTimeout(ApiConstants.WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
        okHttpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        return okHttpClient.build()
    }

    @Singleton
    @Provides
    @Named("retrofit_1")
    fun provideRetrofit1(okHttpClient: OkHttpClient): Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
        return retrofit
    }

    @Provides
    @Singleton
    @Named("httpClientNotification")
    fun provideOkHttpClientForNotification(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.connectTimeout(ApiConstants.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
        okHttpClient.readTimeout(ApiConstants.READ_TIMEOUT, TimeUnit.MILLISECONDS)
        okHttpClient.writeTimeout(ApiConstants.WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
        okHttpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        okHttpClient.addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .addHeader("Authorization", ApiConstants.FIRE_BASE_AUTH_KEY)
                .addHeader("Content-Type", "application/json")
            val request = requestBuilder.build()
            chain.proceed(request)
        }
        return okHttpClient.build()
    }

    @Singleton
    @Provides
    @Named("retrofit_2")
    fun provideRetrofit2(@Named("httpClientNotification") okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL_NOTIFICATION)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }


    @Singleton
    @Provides
    fun provideApiService(@Named("retrofit_1") retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideNotificationMessagingService(@Named("retrofit_2") retrofit: Retrofit): NotificationMessagingService {
        return retrofit.create(NotificationMessagingService::class.java)
    }

    @Provides
    fun provideRepository(
        apiService: ApiService,
        notificationService: NotificationMessagingService,
        lyricsDao: LyricsDao
    ): AppRepository {
        return AppRepoImp(apiService, notificationService, lyricsDao)
    }

   /* @Provides
    @Singleton
    fun provideFirebaseAnalytics(app: Application): FirebaseAnalytics {
        return FirebaseAnalytics.getInstance(app)
    }*/

    @Provides
    @Singleton
    fun provideAdMob(app: Application): AdRequest {
        return AdRequest.Builder().build()
    }
    @Provides
    @Singleton
    fun provideFirebaseStorageReference(): StorageReference {
        return FirebaseStorage.getInstance().reference;
    }
}