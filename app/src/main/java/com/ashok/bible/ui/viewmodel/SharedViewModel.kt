package com.ashok.bible.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.ashok.bible.data.local.entity.QuotesModel
import com.ashok.bible.data.local.entity.StoryModel
import com.ashok.bible.ui.discovery.model.ImageGrid
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    val title = savedStateHandle.getStateFlow("title", null)
    val getImageGridList = savedStateHandle.getLiveData("ImageGridList", emptyList<ImageGrid>())
    val getQuotes = savedStateHandle.getStateFlow("quotesModel", emptyList<QuotesModel>())
    val getQuotesMap = savedStateHandle.getStateFlow("quotesMap", emptyMap<String, List<QuotesModel>>())

    fun putTitle(title: String?) {
        savedStateHandle["title"] = title
    }
    fun putImageGridList(grid: List<ImageGrid>) {
        savedStateHandle["ImageGridList"] = grid
    }

    fun putQuotes(quotes: List<QuotesModel>?) {
        savedStateHandle["quotesModel"] = quotes
    }

    fun putQuotesMap(quotes: Map<String, List<QuotesModel>>?) {
        savedStateHandle["quotesMap"] = quotes
    }

    override fun onCleared() {
        super.onCleared()
        println("ViewModel cleared")
    }

    fun storeImageGridList(
        storyList: List<StoryModel?>? = null,
        quotesTitles: List<ImageGrid>? = null) {
        val imageGrid = ArrayList<ImageGrid>()
        if (storyList != null)
            for (obj in storyList) {
                imageGrid.add(ImageGrid(image = obj?.url, title = obj?.title, des = obj?.description))
            }
        if (quotesTitles != null) {
            imageGrid.addAll(quotesTitles)
        }
        putImageGridList(imageGrid)
    }
}