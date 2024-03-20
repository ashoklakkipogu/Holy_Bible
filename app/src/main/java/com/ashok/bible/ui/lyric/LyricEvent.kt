package com.ashok.bible.ui.lyric

import com.ashok.bible.data.local.entity.LyricsModel

sealed class LyricEvent{
    data class LyricDataMapping(val data: Map<String, LyricsModel>?) : LyricEvent()

}