package com.ashok.bible.ui.lyric

import com.ashok.bible.data.local.entity.LyricsModel
import com.ashok.bible.domain.RequestState

data class LyricState(
    val lyricData: RequestState<List<LyricsModel>?>? = null,
)