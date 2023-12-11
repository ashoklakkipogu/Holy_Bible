package com.ashok.myapplication.ui.screens.tab

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.ashok.myapplication.R
import com.ashok.myapplication.ui.screens.Screens

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabScreen(navController: NavController) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    val tabs = listOf(
        "Notes" to R.drawable.ic_notes_24dp,
        "Bookmark" to R.drawable.ic_bookmarks_24dp,
        "Highlights" to R.drawable.ic_highlight_icon
    )


    val pagerState = rememberPagerState {
        tabs.size
    }
    LaunchedEffect(selectedTabIndex) {
        pagerState.animateScrollToPage(selectedTabIndex)
    }

    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            selectedTabIndex = pagerState.currentPage
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabs.forEachIndexed { index, (title, icon) ->
                Tab(
                    text = { Text(text = title, maxLines = 2, overflow = TextOverflow.Ellipsis) },
                    selected = selectedTabIndex == index,
                    onClick = {
                        selectedTabIndex = index
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = icon),
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                )
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.0f)
        ) { index ->
            when (index) {
                0 -> NoteScreenView {
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("scollId", it)
                    navController.popBackStack()

                    /*navController.navigate(Screens.Bible.router) {
                        popUpTo(navController.graph.id){
                            inclusive = true
                        }

                    }*/
                }

                1 -> FavScreenView {
                    navController.navigate(Screens.Bible.router) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                }

                2 -> HighlightScreenView {
                    navController.navigate(Screens.Bible.router) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        }

    }
}
