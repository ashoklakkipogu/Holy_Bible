package com.ashok.bible.ui.lyric

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashok.bible.data.local.entity.LyricsModel
import com.ashok.bible.domain.repository.BibleRepository
import com.ashok.bible.ui.utilities.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LyricViewModel @Inject constructor(
    val repository: BibleRepository
) : ViewModel() {

    init {
        getLyrics()
    }

    var state by mutableStateOf(LyricState())

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