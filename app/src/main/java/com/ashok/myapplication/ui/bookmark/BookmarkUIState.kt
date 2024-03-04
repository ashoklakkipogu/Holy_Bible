package com.ashok.myapplication.ui.bookmark

import androidx.compose.runtime.State
import com.ashok.myapplication.data.local.model.FavModel
import com.ashok.myapplication.data.local.model.HighlightModel
import com.ashok.myapplication.data.local.model.NoteModel

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
)
