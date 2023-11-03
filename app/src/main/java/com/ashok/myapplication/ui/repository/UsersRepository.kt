package com.ashok.myapplication.ui.repository

import com.ashok.myapplication.data.datasource.UserDataSource
import com.ashok.myapplication.data.entity.Users
import com.ashok.myapplication.ui.utilities.Result
import javax.inject.Inject

class UsersRepository @Inject constructor(private val userDataSource: UserDataSource) {

    suspend fun getUserList(pageIndex: Int): Result<Users> {
        val userList = userDataSource.getUserList(pageIndex)
        return if (userList.isSuccessful && userList.body() != null) {
            Result.Success(userList.body()!!)
        } else {
            Result.Error(userList.message())
        }
    }
}