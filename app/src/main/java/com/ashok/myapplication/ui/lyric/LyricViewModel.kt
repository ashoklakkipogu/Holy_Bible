package com.ashok.myapplication.ui.lyric

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashok.myapplication.data.local.entity.LyricsModel
import com.ashok.myapplication.domain.repository.BibleRepository
import com.ashok.myapplication.ui.utilities.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LyricViewModel @Inject constructor(
    val repository: BibleRepository
) : ViewModel() {

    init {
        getLyrics()
    }

    var state by mutableStateOf(LyricState())

    fun onEvent(event: LyricEvent) {

    }

    private fun getLyrics() {
        viewModelScope.launch {
            repository.getLyrics().collect { result ->
                when (result) {
                    is Result.Error -> {
                        state = state.copy(
                            error = result.apiError?.getErrorMessage(),
                            isLoading = false
                        )
                    }

                    is Result.Loading -> {
                        state = state.copy(isLoading = true)
                    }

                    is Result.Success -> {
                        val data = result.data
                        val list = ArrayList<LyricsModel>()
                        if (data != null) {
                            for ((key, value) in data) {
                                val sample: LyricsModel = value
                                sample.lyricId = key
                                list.add(sample)
                            }
                        }
                        state = state.copy(
                            lyric = list,
                            isLoading = false
                        )

                    }
                }
            }
        }

    }
}