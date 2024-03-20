package com.ashok.bible.ui.dashboard

import com.ashok.bible.data.local.entity.StatusEmptyImagesModel
import com.ashok.bible.data.local.entry.BibleIndexModelEntry
import com.ashok.bible.data.local.entry.BibleModelEntry

data class DashboardUiState(
    var bibleData: List<BibleModelEntry>? = null,
    var bibleIndexData: List<BibleIndexModelEntry>? = null,
    var isLoading: Boolean = false,
    var bibleScrollPos: Int = 0,
    var expandedState: String = "",
    var selectedLanguage: String = "",


    val statusImages: List<StatusEmptyImagesModel>? = null,

    )
