package com.ashok.bible.ui.lyric

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.ashok.bible.data.local.entity.LyricsModel
import com.ashok.bible.ui.bibleindex.components.TopAppBarView
import com.ashok.bible.ui.component.DetailView

@Composable
fun LyricDetails(model: LyricsModel?, onBackPress:()->Unit) {
    Column {
        TopAppBarView(model?.title?:"Lyric") {
            onBackPress.invoke()
        }
        DetailView(
            youtubeId = model?.youtubeId,
            description = model?.content,
            title = model?.title
        )
    }

}