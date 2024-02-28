package com.ashok.myapplication.ui.lyric

import com.ashok.myapplication.data.entity.LyricsModel
import com.ashok.myapplication.data.entity.Users
import com.ashok.myapplication.ui.utilities.Result

data class LyricState(
    val lyrics: Result<List<LyricsModel>> = Result.Loading()
)