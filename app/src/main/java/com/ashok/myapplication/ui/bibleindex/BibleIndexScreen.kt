package com.ashok.myapplication.ui.bibleindex

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.ashok.myapplication.ui.bibleindex.components.ExpandableCard
import com.ashok.myapplication.ui.bibleindex.components.SearchBarView
import com.ashok.myapplication.ui.bibleindex.components.TopAppBarView
import com.ashok.myapplication.ui.dashboard.DashboardUiEvent
import com.ashok.myapplication.ui.dashboard.DashboardUiState
import com.ashok.myapplication.ui.navgraph.Route
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BibleIndexScreen(
    navController: NavController,
    state: DashboardUiState,
    event: (DashboardUiEvent) ->Unit
    ) {
    val bibleIndexData = state.bibleIndexData

    var searchText by remember { mutableStateOf("") } // Query for SearchBar

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    DisposableEffect(Unit) {
        onDispose {
            event(DashboardUiEvent.TextSpeechStop)
        }
    }
    Column(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxSize()

    ) {
        TopAppBarView("References") {
            navController.popBackStack()
        }
        Box(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
        ){
            SearchBarView(
                text = searchText,
                readOnly = false,
                onValueChange = { searchText = it},
                onSearch = {
                    //searchText=it
                }
            )
        }

        Box(
            modifier = Modifier
                .padding(16.dp)
        ) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                state = listState
            ) {
               /* itemsIndexed(bibleIndexData.filter { it.chapter.contains(searchText, ignoreCase = true) }){index, model ->
                    model.isExpand = viewModel.expandedState == model.chapter
                    ExpandableCard(
                        data = model,
                        viewModel = viewModel,
                        onClick = {
                            currentIndex = index
                            viewModel.expandedState = it.chapter
                            //event(BibleIndexEvent.ChaptersByBookIdAndLangauge(it.chapter_id))
                            coroutineScope.launch {
                                listState.animateScrollToItem(currentIndex)
                            }
                        },
                        onClickIndex = {bookId, chapterId->
                            navController.navigate(route = Route.BibleIndexDetails.getFullRoute(bookId = bookId, chapterId = chapterId))
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                */

                bibleIndexData?.forEachIndexed { i, dataItem ->
                    dataItem.isExpand = state.expandedState == dataItem.chapter
                    if (dataItem.isExpand){
                        coroutineScope.launch {
                            listState.animateScrollToItem(i)
                        }
                    }

                    item(key = "header_$i") {
                        ExpandableCard(
                            data = dataItem,
                            onClick = {
                                event(DashboardUiEvent.ExpandedState(it.chapter))
                            },
                            onClickIndex = {bookId, chapterId->
                                navController.navigate(route = Route.BibleIndexDetails.getFullRoute(bookId = bookId, chapterId = chapterId))
                            },
                            onSoundClick = {
                                event(DashboardUiEvent.TextSpeechPlay(it))
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                }

            }
        }

    }
}
