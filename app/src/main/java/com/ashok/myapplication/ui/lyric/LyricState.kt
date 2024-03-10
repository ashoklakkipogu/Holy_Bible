package com.ashok.myapplication.ui.lyric

import com.ashok.myapplication.data.local.entity.LyricsModel
import com.ashok.myapplication.data.local.entity.Users
import com.ashok.myapplication.ui.utilities.Result

data class LyricState(
    val isLoading: Boolean = false,
    val lyric: List<LyricsModel>? = null,
    val error: String? = null
)