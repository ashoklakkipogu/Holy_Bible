package com.ashok.myapplication.ui.bookmark

import com.ashok.myapplication.data.local.model.FavModel
import com.ashok.myapplication.data.local.model.HighlightModel
import com.ashok.myapplication.data.local.model.NoteModel

sealed class BookmarkUIEvent {
    object GetAllNoteBibleData : BookmarkUIEvent()
    object GetAllFavBibleData : BookmarkUIEvent()
    object GetAllHighlightBibleData : BookmarkUIEvent()
    data class DeleteNoteById(val obj: NoteModel) : BookmarkUIEvent()
    data class DeleteFavById(val obj: FavModel) : BookmarkUIEvent()
    data class DeleteHighlightById(val obj: HighlightModel) : BookmarkUIEvent()

}
