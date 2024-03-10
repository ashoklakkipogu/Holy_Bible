package com.ashok.myapplication.ui.discovery

import com.ashok.myapplication.data.local.entity.QuotesModel
import com.ashok.myapplication.data.local.entity.StatusImagesModel
import com.ashok.myapplication.data.local.entity.StoryModel
import com.ashok.myapplication.ui.discovery.model.ImageGrid

data class DiscoveryUIState(
    var quotesTitles: List<ImageGrid>? = null,
    var quotesMap: Map<String, List<QuotesModel>> = emptyMap(),
    var isLoadingQuotes: Boolean = true,
    var errorQuote: String? = null,

    var isLoadingStory: Boolean = true,
    var storyList: List<StoryModel>? = null,
    var errorStory: String? = null,

    var isLoadingStatus: Boolean = true,
    var statusList: List<StatusImagesModel>? = null,
    var errorStatus: String? = null,

    )
