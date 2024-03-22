package com.ashok.bible.ui.dashboard

import com.ashok.bible.data.local.entity.StatusEmptyImagesModel
import com.ashok.bible.data.local.entry.BibleIndexModelEntry
import com.ashok.bible.data.local.entry.BibleModelEntry
import com.ashok.bible.domain.RequestState

data class DashboardUiState(
    var bibleData: RequestState<List<BibleModelEntry>?>? = null,
    var bibleIndexData: RequestState<List<BibleIndexModelEntry>?>? = null,
    var bibleScrollPos: Int = 0,
    var expandedState: String = "",
    var selectedLanguage: String = "",
    val statusImages: List<StatusEmptyImagesModel>? = null
)
