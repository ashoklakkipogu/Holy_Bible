package com.ashok.bible.ui.lyric

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashok.bible.data.local.entity.LyricsModel
import com.ashok.bible.domain.repository.BibleRepository
import com.ashok.bible.domain.usecase.BibleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LyricViewModel @Inject constructor(
    val repository: BibleRepository,
    val useCase: BibleUseCase
) : ViewModel() {


    init {
        getLyrics()
    }

    var state by mutableStateOf(LyricState())

    private fun getLyrics() {
        viewModelScope.launch {
            useCase.getLyrics().collect {
                state = state.copy(lyricData = it)
            }
        }
    }

    fun lyricMapping() {}
}