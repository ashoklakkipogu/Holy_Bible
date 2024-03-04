package com.ashok.myapplication.ui.dashboard

import com.ashok.myapplication.data.local.entry.BibleModelEntry

sealed class DashboardUiEvent {
    data class SetOrResetBibleScrollPos(val pos: Int) : DashboardUiEvent()
    data class HighlightInsertOrDelete(val bibleVerse: BibleModelEntry) : DashboardUiEvent()
    data class BookmarkInsertOrDelete(val bibleVerse: BibleModelEntry) : DashboardUiEvent()
    data class NoteInsert(val bibleVerse: BibleModelEntry, val title: String) : DashboardUiEvent()
    data class GetBibleActionForLeftRight(
        val clickAction: String = "",
        val bookId: Int = 1,
        val chapterId: Int = 1,
        val isScrollTop: Int = 0
    ) : DashboardUiEvent()

    data class ExpandedState(val expandedState: String = "") : DashboardUiEvent()
}