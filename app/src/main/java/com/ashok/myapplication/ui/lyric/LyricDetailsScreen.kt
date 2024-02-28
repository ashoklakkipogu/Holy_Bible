package com.ashok.myapplication.ui.lyric

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.ashok.myapplication.data.entity.LyricsModel
import com.ashok.myapplication.ui.bibleindex.components.TopAppBarView
import com.ashok.myapplication.ui.component.DetailView
import com.ashok.myapplication.ui.navgraph.Route

@Composable
fun LyricDetails(model: LyricsModel?, onBackPress:()->Unit) {
    Column {
        TopAppBarView("Lyric") {
            onBackPress.invoke()
        }
        DetailView(
            youtubeId = model?.youtubeId,
            description = model?.content,
            title = model?.title
        )
    }

}