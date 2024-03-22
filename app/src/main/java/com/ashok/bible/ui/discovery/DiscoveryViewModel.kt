package com.ashok.bible.ui.discovery

import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashok.bible.domain.usecase.BibleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoveryViewModel @Inject constructor(
    val useCase: BibleUseCase,
    val pref: SharedPreferences
) : ViewModel() {

    var state by mutableStateOf(DiscoveryUIState())
        private set

    init {
        getQuotes()
        getStories()
        getStatusImages()
    }

    private fun getQuotes() {
        viewModelScope.launch {
            useCase.getQuotes().collect { result ->
                state = state.copy(quotesData = result)
            }
        }
    }

    private fun getStories() {
        viewModelScope.launch {
            useCase.getStory().collect { result ->
                state = state.copy(storyData = result)
            }
        }

    }

    private fun getStatusImages() {
        viewModelScope.launch {
            useCase.getStatusImages().collect { result ->
                state = state.copy(statusImagesData = result)
            }
        }
    }
}