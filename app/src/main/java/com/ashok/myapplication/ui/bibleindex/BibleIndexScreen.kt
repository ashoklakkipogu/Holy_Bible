package com.ashok.myapplication.ui.bibleindex

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import com.ashok.myapplication.ui.bibleindex.components.SearchBar
import com.ashok.myapplication.ui.bibleindex.components.SearchBarView
import com.ashok.myapplication.ui.bibleindex.components.TopAppBarView
import com.ashok.myapplication.ui.navgraph.Route
import kotlinx.coroutines.launch
import kotlin.reflect.KFunction1

@Composable
fun BibleIndexScreen(
    viewModel: BibleIndexViewModel,
    navController: NavController,
    event: (BibleIndexEvent) -> Unit
) {
    var searchText by remember { mutableStateOf("") } // Query for SearchBar

    val bibleIndexData = viewModel.bibileIndex
    val listState = rememberLazyListState()
    var currentIndex by remember {
        mutableIntStateOf(0)
    }
    val coroutineScope = rememberCoroutineScope()


    var expandedState by remember {
        mutableStateOf("")
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
                itemsIndexed(bibleIndexData.filter { it.chapter.contains(searchText, ignoreCase = true) }){index, model ->
                    model.isExpand = expandedState == model.chapter
                    ExpandableCard(
                        data = model,
                        viewModel = viewModel,
                        onClick = {
                            currentIndex = index
                            expandedState = it.chapter
                            event(BibleIndexEvent.ChaptersByBookIdAndLangauge(it.chapter_id))
                        },
                        onClickIndex = {
                            navController.navigate(Route.BibleIndexDetails.router)
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                coroutineScope.launch {
                    listState.animateScrollToItem(currentIndex)
                }

            }
        }

    }
}
