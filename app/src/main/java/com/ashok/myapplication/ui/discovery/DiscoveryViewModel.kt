package com.ashok.myapplication.ui.discovery

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashok.myapplication.data.local.entity.LyricsModel
import com.ashok.myapplication.data.local.entity.StoryModel
import com.ashok.myapplication.domain.repository.BibleRepository
import com.ashok.myapplication.ui.utilities.Result
import com.ashok.myapplication.ui.utilities.SharedPrefUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoveryViewModel @Inject constructor(
    val repository: BibleRepository,
    val pref: SharedPreferences
) : ViewModel() {

    var state by mutableStateOf(DiscoveryUIState())
        private set

    init {
        getQuotes()
        getStories()
    }

    private fun getQuotes() {
        viewModelScope.launch {
            repository.getQuotes().collect{ result->
                when(result){
                    is Result.Error -> {
                        Log.i("data", "data..........e"+result.message)
                        state = state.copy(isLoadingQuotes = false, errorQuote = result.message)
                    }
                    is Result.Loading -> {
                        Log.i("data", "data..........l")
                        state = state.copy(isLoadingQuotes = true)
                    }
                    is Result.Success -> {
                        val modelList: ArrayList<String> = ArrayList()
                        result.data?.let {
                            for (obj in it.keys) {
                                modelList.add(obj)
                            }
                            state = state.copy(isLoadingQuotes = false, quotesTitles = modelList)
                            Log.i("data", "data..........d"+it.size)

                        }
                        Log.i("data", "data..........S"+modelList.size)

                    }
                }
            }
        }

    }

    private fun getStories() {
        viewModelScope.launch {
            repository.getStory().collect { result ->
                when (result) {
                    is Result.Error -> {
                        state = state.copy(
                            errorStory = result.message,
                            isLoadingStory = false
                        )
                    }

                    is Result.Loading -> {
                        state = state.copy(isLoadingStory = true)
                    }

                    is Result.Success -> {
                        result.data?.let { data ->
                            val list = ArrayList<StoryModel>()
                            for ((_, value) in data) {
                                list.add(value)
                            }
                            state = state.copy(
                                storyList = list,
                                isLoadingStory = false
                            )
                        }
                    }
                }
            }
        }

    }
}