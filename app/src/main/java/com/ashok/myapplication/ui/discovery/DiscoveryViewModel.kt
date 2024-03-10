package com.ashok.myapplication.ui.discovery

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashok.myapplication.data.local.entity.LyricsModel
import com.ashok.myapplication.data.local.entity.StatusImagesModel
import com.ashok.myapplication.data.local.entity.StoryModel
import com.ashok.myapplication.domain.repository.BibleRepository
import com.ashok.myapplication.ui.discovery.model.ImageGrid
import com.ashok.myapplication.ui.utilities.Result
import com.ashok.myapplication.ui.utilities.SharedPrefUtils
import com.ashok.myapplication.ui.viewmodel.SharedViewModel
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
        getStatusImages()
    }


    private fun getQuotes() {
        viewModelScope.launch {
            repository.getQuotes().collect { result ->
                when (result) {
                    is Result.Error -> {
                        //Log.i("data", "data..........e" + result.apiError?.getErrorMessage())
                        state = state.copy(isLoadingQuotes = false, errorQuote = result.apiError?.getErrorMessage())
                    }

                    is Result.Loading -> {
                        //Log.i("data", "data..........l")
                        state = state.copy(isLoadingQuotes = true)
                    }

                    is Result.Success -> {
                        //Log.i("data", "data..........s" + result.data.toString())

                        val modelList: ArrayList<ImageGrid> = ArrayList()
                        val data = result.data
                        if (data != null) {
                            for (obj in data.keys) {
                                modelList.add(ImageGrid(title = obj))
                            }
                            state = state.copy(isLoadingQuotes = false, quotesTitles = modelList)
                            state = state.copy(isLoadingQuotes = false, quotesMap = data)
                        } else {
                            state = state.copy(isLoadingQuotes = false)
                        }


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
                            errorStory = result.apiError?.getErrorMessage(),
                            isLoadingStory = false
                        )
                    }

                    is Result.Loading -> {
                        state = state.copy(isLoadingStory = true)
                    }

                    is Result.Success -> {
                        val data = result.data
                        state = if (data != null) {
                            val list = ArrayList<StoryModel>()
                            for ((_, value) in data) {
                                list.add(value)
                            }
                            val newList = list.sortedBy { sort ->
                                sort.createdDate
                            }.reversed()
                            state.copy(
                                storyList = newList,
                                isLoadingStory = false
                            )
                        } else {
                            state.copy(
                                isLoadingStory = false
                            )
                        }

                    }
                }
            }
        }

    }

    private fun getStatusImages() {
        viewModelScope.launch {
            repository.getStatusImages().collect { result ->
                when (result) {
                    is Result.Error -> {
                        state = state.copy(
                            errorStatus = result.apiError?.getErrorMessage(),
                            isLoadingStatus = false
                        )
                    }

                    is Result.Loading -> {
                        state = state.copy(isLoadingStatus = true)
                    }

                    is Result.Success -> {
                        val data = result.data
                        if (data != null) {
                            val list = ArrayList<StatusImagesModel>()
                            for ((_, value) in data) {
                                list.add(value)
                            }
                            val newList = list.sortedBy { sort ->
                                sort.createdDate
                            }.reversed()
                            state = state.copy(
                                statusList = newList,
                                isLoadingStatus = false
                            )
                        } else {
                            state = state.copy(
                                isLoadingStatus = false
                            )
                        }
                    }
                }
            }
        }
    }
}