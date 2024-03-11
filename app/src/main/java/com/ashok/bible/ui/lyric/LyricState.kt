package com.ashok.bible.ui.lyric

import com.ashok.bible.data.local.entity.LyricsModel

data class LyricState(
    val isLoading: Boolean = false,
    val lyric: List<LyricsModel>? = null,
    val error: String? = null
)