package com.ashok.bible.ui.bookmark

import com.ashok.bible.data.local.model.FavModel
import com.ashok.bible.data.local.model.HighlightModel
import com.ashok.bible.data.local.model.NoteModel
import com.ashok.bible.domain.RequestState

data class BookmarkUIState(
    val isFavLoading: Boolean = false,
    val isHighlightLoading: Boolean = false,
    val isNoteLoading: Boolean = false,
    val noteData: RequestState<List<NoteModel>?>? = null,
    val favData: RequestState<List<FavModel>?>? = null,
    val highlightData: RequestState<List<HighlightModel>?>? = null,
    val noteDelete: Boolean = false,
    val favDelete: Boolean = false,
    val highlightDelete: Boolean = false
)
