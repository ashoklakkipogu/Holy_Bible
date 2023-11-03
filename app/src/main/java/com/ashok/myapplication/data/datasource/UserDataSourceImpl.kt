package com.ashok.myapplication.data.datasource

import com.ashok.myapplication.data.api.ApiService
import com.ashok.myapplication.data.entity.Users
import retrofit2.Response
import javax.inject.Inject

class UserDataSourceImpl  @Inject constructor(val apiService: ApiService):UserDataSource {
    override suspend fun getUserList(page: Int): Response<Users> {
        return apiService.getUserList(page)
    }
}