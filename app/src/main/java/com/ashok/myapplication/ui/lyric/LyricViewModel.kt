package com.ashok.myapplication.ui.lyric

import android.content.SharedPreferences
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashok.myapplication.data.entity.LyricsModel
import com.ashok.myapplication.data.entity.Users
import com.ashok.myapplication.data.local.repositary.DbRepository
import com.ashok.myapplication.ui.repository.LyricRepository
import com.ashok.myapplication.ui.utilities.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LyricViewModel @Inject constructor(
    val repository: LyricRepository
) : ViewModel() {

    init {
        getLyrics()
    }
    var lyrics by mutableStateOf(LyricState())
        private set

    fun onEvent(event: LyricEvent) {

    }
    fun getLyrics() {
        viewModelScope.launch{
            val result = repository.getAllLyrics()
            lyrics = lyrics.copy(lyrics = result)
        }

    }
}