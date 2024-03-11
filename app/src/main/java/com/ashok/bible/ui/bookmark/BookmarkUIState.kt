package com.ashok.bible.ui.bookmark

import com.ashok.bible.data.local.model.FavModel
import com.ashok.bible.data.local.model.HighlightModel
import com.ashok.bible.data.local.model.NoteModel

data class BookmarkUIState(
    val isFavLoading: Boolean = false,
    val isHighlightLoading: Boolean = false,
    val isNoteLoading: Boolean = false,
    val noteData: List<NoteModel>? = null,
    val favData: List<FavModel>? = null,
    val highlightData: List<HighlightModel>? = null,
    val noteDelete: Boolean = false,
    val favDelete: Boolean = false,
    val highlightDelete: Boolean = false,

    val noteDataError: Boolean = false,
    val favDataError: Boolean = false,
    val highlightDataError: Boolean = false,

    )
