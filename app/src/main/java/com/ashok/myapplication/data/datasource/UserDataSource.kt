package com.ashok.myapplication.data.datasource

import com.ashok.myapplication.data.entity.Users
import retrofit2.Response

interface UserDataSource {
    suspend fun getUserList(page:Int):Response<Users>
}