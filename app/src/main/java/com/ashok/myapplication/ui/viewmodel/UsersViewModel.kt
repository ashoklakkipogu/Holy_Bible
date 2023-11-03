package com.ashok.myapplication.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashok.myapplication.data.entity.Users
import com.ashok.myapplication.ui.repository.UsersRepository
import com.ashok.myapplication.ui.utilities.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(val repository: UsersRepository) : ViewModel() {

    private val _userData = MutableLiveData<Result<Users>>(Result.Loading())
    val userData: LiveData<Result<Users>> get() = _userData
    fun getUserList(pageIndex: Int) {
        viewModelScope.launch{
            val result = repository.getUserList(pageIndex)
            _userData.value = result
        }

    }
}