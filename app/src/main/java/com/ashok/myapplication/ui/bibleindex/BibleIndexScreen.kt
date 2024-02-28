package com.ashok.myapplication.ui.bibleindex

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ashok.myapplication.data.local.entry.BibleIndexModelEntry
import com.ashok.myapplication.ui.bibleindex.components.ExpandableCard
import com.ashok.myapplication.ui.bibleindex.components.IndexView
import com.ashok.myapplication.ui.bibleindex.components.SearchBar
import com.ashok.myapplication.ui.bibleindex.components.SearchBarView
import com.ashok.myapplication.ui.bibleindex.components.TopAppBarView
import com.ashok.myapplication.ui.navgraph.Route
import com.ashok.myapplication.ui.viewmodel.HomeViewModel
import kotlinx.coroutines.launch
import kotlin.reflect.KFunction1

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BibleIndexScreen(
    viewModel: HomeViewModel,
    navController: NavController
) {
    val bibleIndexData = viewModel.bibileIndex

    var searchText by remember { mutableStateOf("") } // Query for SearchBar

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

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

                bibleIndexData.forEachIndexed { i, dataItem ->
                    dataItem.isExpand = viewModel.expandedState == dataItem.chapter
                    if (dataItem.isExpand){
                        coroutineScope.launch {
                            listState.animateScrollToItem(i)
                        }
                    }

                    item(key = "header_$i") {
                        ExpandableCard(
                            data = dataItem,
                            onClick = {
                                viewModel.expandedState = it.chapter

                            },
                            onClickIndex = {bookId, chapterId->
                                navController.navigate(route = Route.BibleIndexDetails.getFullRoute(bookId = bookId, chapterId = chapterId))
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                }

            }
        }

    }
}
