package com.ashok.bible.ui.bibleindex

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ashok.bible.ui.bibleindex.components.IndexView
import com.ashok.bible.ui.bibleindex.components.TopAppBarView

@Composable
fun BibleIndexDetailsScreen(
    viewModel: BibleIndexDetailsViewModel,
    navController: NavController,
    bookId: Int,
    chapterId: Int,
    event: (BibleIndexDetailsEvent) -> Unit,
    onClickIndex: (Int) -> Unit
) {
    event(BibleIndexDetailsEvent.VerseByBookIdAndLangauge(bookId, chapterId))
    val verseListData = viewModel.verseList
    Column(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxSize()

    ) {
        TopAppBarView("Select Verse") {
            navController.popBackStack()
        }
        Box(
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(6)
            ) {
                itemsIndexed(verseListData) { index, model ->
                    IndexView(title = model) {
                        onClickIndex.invoke(it)
                    }
                }
            }
        }
    }
}

