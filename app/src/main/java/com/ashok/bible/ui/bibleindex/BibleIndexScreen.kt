package com.ashok.bible.ui.bibleindex

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ashok.bible.ui.bibleindex.components.ExpandableCard
import com.ashok.bible.ui.bibleindex.components.TopAppBarView
import com.ashok.bible.ui.dashboard.DashboardUiEvent
import com.ashok.bible.ui.dashboard.DashboardUiState
import com.ashok.bible.ui.lyric.component.SearchAppBar
import com.ashok.bible.ui.navgraph.Route

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BibleIndexScreen(
    navController: NavController,
    state: DashboardUiState,
    event: (DashboardUiEvent) -> Unit
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
        /*Box(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
        ) {
            SearchBarView(
                text = searchText,
                readOnly = false,
                onValueChange = { searchText = it },
                onSearch = {
                    //searchText=it
                }
            )
        }*/
        SearchAppBar() {
            searchText = it
        }

        Box(
            modifier = Modifier
                .padding(16.dp)
        ) {
            if (bibleIndexData != null)
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    state = listState,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    itemsIndexed(items = bibleIndexData.filter {
                        it.chapter.contains(
                            searchText,
                            ignoreCase = true
                        )
                    }) { i, dataItem ->
                        dataItem.isExpand = state.expandedState == dataItem.chapter
                        if (dataItem.isExpand) {
                            LaunchedEffect(Unit) {
                                listState.animateScrollToItem(i)
                            }
                        }

                        ExpandableCard(
                            data = dataItem,
                            onClick = {
                                event(DashboardUiEvent.ExpandedState(it.chapter))
                            },
                            onClickIndex = { bookId, chapterId ->
                                navController.navigate(
                                    route = Route.BibleIndexDetails.getFullRoute(
                                        bookId = bookId,
                                        chapterId = chapterId
                                    )
                                )
                            },
                            onSoundClick = {
                                event(DashboardUiEvent.TextSpeechPlay(it))
                            }
                        )
                       // Spacer(modifier = Modifier.height(8.dp))

                    }


                    /*bibleIndexData?.forEachIndexed { i, dataItem ->
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

                    }*/

                }
        }

    }
}
