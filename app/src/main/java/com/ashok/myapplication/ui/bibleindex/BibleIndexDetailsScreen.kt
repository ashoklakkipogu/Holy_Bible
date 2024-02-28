package com.ashok.myapplication.ui.bibleindex

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ashok.myapplication.data.local.entry.BibleIndexModelEntry
import com.ashok.myapplication.ui.bibleindex.components.ExpandableCard
import com.ashok.myapplication.ui.bibleindex.components.IndexView
import com.ashok.myapplication.ui.bibleindex.components.TopAppBarView
import com.ashok.myapplication.ui.navgraph.Route
import kotlinx.coroutines.launch
import kotlin.reflect.KFunction1

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

