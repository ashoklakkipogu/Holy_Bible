package com.ashok.bible.ui.discovery

import com.ashok.bible.data.local.entity.QuotesModel
import com.ashok.bible.data.local.entity.StatusImagesModel
import com.ashok.bible.data.local.entity.StoryModel

sealed class DiscoveryUIEvent {
    data class QuotesTitlesMapping(val quotes: Map<String, List<QuotesModel>>?) : DiscoveryUIEvent()
    data class StoryMapping(val story: Map<String, StoryModel>?) : DiscoveryUIEvent()
    data class StatusMapping(val status: Map<String, StatusImagesModel>?) : DiscoveryUIEvent()
}
