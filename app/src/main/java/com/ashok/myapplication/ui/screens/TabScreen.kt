package com.ashok.myapplication.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ashok.myapplication.R
import com.ashok.myapplication.ui.screens.tab.FavScreenView
import com.ashok.myapplication.ui.screens.tab.HighlightScreenView
import com.ashok.myapplication.ui.screens.tab.NoteScreenView
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabScreen() {
    var selectedTabIndex by remember { mutableStateOf(0) }

    val tabs = listOf(
        "Notes" to R.drawable.ic_notes_24dp,
        "Bookmark" to R.drawable.ic_bookmarks_24dp,
        "Highlights" to R.drawable.ic_highlight_icon
    )
    val pagerState = rememberPagerState {
        tabs.size
    }

    val coroutineScope = rememberCoroutineScope()


    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabs.forEachIndexed { index, (title, icon) ->
                Tab(
                    text = { Text(text = title, maxLines = 2, overflow = TextOverflow.Ellipsis) },
                    selected = selectedTabIndex == index,
                    onClick = {
                        selectedTabIndex = index
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(selectedTabIndex)
                        }
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
        /*when (tabIndex) {
            0 -> NoteScreenView()
            1 -> FavScreenView()
            2 -> HighlightScreenView()
        }*/
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { index ->
            when (index) {
                0 -> NoteScreenView()
                1 -> FavScreenView()
                2 -> HighlightScreenView()
            }
        }

    }
    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (!pagerState.isScrollInProgress) {
            selectedTabIndex = pagerState.currentPage
        }
    }

}
