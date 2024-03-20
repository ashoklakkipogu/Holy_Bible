package com.ashok.bible.ui.lyric

import com.ashok.bible.data.local.entity.LyricsModel
import com.ashok.bible.domain.RequestState
import kotlinx.coroutines.flow.Flow

data class LyricState(
   /* val isLoading: Boolean = false,
    val lyric: List<LyricsModel>? = null,
    val error: String? = null,*/
    val lyricData: RequestState<Map<String, LyricsModel>?>? = null,
    val lyricMappingData: List<LyricsModel>? = null

)