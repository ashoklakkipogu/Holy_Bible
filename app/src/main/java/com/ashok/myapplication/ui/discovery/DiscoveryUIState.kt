package com.ashok.myapplication.ui.discovery

import com.ashok.myapplication.data.local.entity.StoryModel

data class DiscoveryUIState(
    var quotesTitles: List<String> = emptyList(),
    var isLoadingQuotes: Boolean = true,
    var isLoadingStory: Boolean = true,
    var errorQuote: String? = null,

    var storyList: List<StoryModel> = emptyList(),
    var errorStory: String? = null,


    )
