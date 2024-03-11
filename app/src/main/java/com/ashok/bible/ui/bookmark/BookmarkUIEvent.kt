package com.ashok.bible.ui.bookmark

import com.ashok.bible.data.local.model.FavModel
import com.ashok.bible.data.local.model.HighlightModel
import com.ashok.bible.data.local.model.NoteModel

sealed class BookmarkUIEvent {
    object GetAllNoteBibleData : BookmarkUIEvent()
    object GetAllFavBibleData : BookmarkUIEvent()
    object GetAllHighlightBibleData : BookmarkUIEvent()
    data class DeleteNoteById(val obj: NoteModel) : BookmarkUIEvent()
    data class DeleteFavById(val obj: FavModel) : BookmarkUIEvent()
    data class DeleteHighlightById(val obj: HighlightModel) : BookmarkUIEvent()

}
