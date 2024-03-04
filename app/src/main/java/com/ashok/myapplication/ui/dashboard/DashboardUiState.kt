package com.ashok.myapplication.ui.dashboard

import com.ashok.myapplication.data.local.entry.BibleIndexModelEntry
import com.ashok.myapplication.data.local.entry.BibleModelEntry

data class DashboardUiState(
    var bibleData:List<BibleModelEntry>? = null,
    var bibleIndexData:List<BibleIndexModelEntry>? = null,
    var isLoading:Boolean = false,
    var error:String? = null,
    var bibleScrollPos:Int = 0,
    var expandedState:String = ""
)
