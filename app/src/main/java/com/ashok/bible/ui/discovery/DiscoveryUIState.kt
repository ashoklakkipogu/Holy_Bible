package com.ashok.bible.ui.discovery

import com.ashok.bible.data.local.entity.QuotesModel
import com.ashok.bible.data.local.entity.StatusImagesModel
import com.ashok.bible.data.local.entity.StoryModel
import com.ashok.bible.domain.RequestState
import com.ashok.bible.ui.discovery.model.ImageGrid

data class DiscoveryUIState(
    /*var quotesTitles: List<ImageGrid>? = null,
    var quotesMap: Map<String, List<QuotesModel>> = emptyMap(),
    var isLoadingQuotes: Boolean = true,
    var errorQuote: String? = null,

    var isLoadingStory: Boolean = true,
    var storyList: List<StoryModel>? = null,
    var errorStory: String? = null,

    var isLoadingStatus: Boolean = true,
    var errorStatus: String? = null,*/

    var quotesData: RequestState<Map<String, List<QuotesModel>>?>? = null,
    var storyData: RequestState<Map<String, StoryModel>?>? = null,
    var statusImagesData: RequestState<Map<String, StatusImagesModel>?>? = null,

    var quotesTitlesMapping: List<ImageGrid>? = null,
    var quotesMap: Map<String, List<QuotesModel>>? = null,
    var storyList: List<StoryModel>? = null,
    var statusList: List<StatusImagesModel>? = null,



    )
