package com.ashok.bible.domain.model

import com.ashok.bible.data.local.entity.QuotesModel
import com.ashok.bible.ui.discovery.model.ImageGrid

data class QuotesMappingModel(
    var quotesTitles: List<ImageGrid>? = null,
    var quotesMap: Map<String, List<QuotesModel>>? = null,
)