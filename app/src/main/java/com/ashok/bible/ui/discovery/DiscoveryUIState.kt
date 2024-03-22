package com.ashok.bible.ui.discovery

import com.ashok.bible.data.local.entity.QuotesModel
import com.ashok.bible.data.local.entity.StatusImagesModel
import com.ashok.bible.data.local.entity.StoryModel
import com.ashok.bible.domain.RequestState
import com.ashok.bible.domain.model.QuotesMappingModel
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

    var quotesData: RequestState<QuotesMappingModel>? = null,
    var storyData: RequestState<List<StoryModel>?>? = null,
    var statusImagesData: RequestState<List<StatusImagesModel>?>? = null,



    )
