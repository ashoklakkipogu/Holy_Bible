package com.ashok.myapplication.ui.bibleindex

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ashok.myapplication.ui.bibleindex.components.ExpandableCard


sealed class BibleIndexEvent{
    data class ChaptersByBookIdAndLangauge(val id: Int) : BibleIndexEvent()
    data class VerseByBookIdAndLangauge(val bookId:Int, val chapterID:Int) : BibleIndexEvent()
}