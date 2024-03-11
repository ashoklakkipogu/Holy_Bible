package com.ashok.bible.ui.bookmark

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ashok.bible.ui.bookmark.tab.TabScreen
import com.ashok.bible.ui.dashboard.DashboardUiEvent
import com.ashok.bible.ui.theme.BibleTheme

@Composable
fun BookmarkScreen(
    navController: NavController,
    state: BookmarkUIState,
    event: (BookmarkUIEvent) -> Unit,
    dashboardEvent: (DashboardUiEvent) -> Unit
) {
    BibleTheme {
        Surface(
            modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.background
        ) {
            TabScreen(navController, state = state, event = event, dashboardEvent = dashboardEvent)
        }
    }
}


@Preview
@Composable
fun BookmarkScreenPreview() {
    BookmarkScreen(rememberNavController(), state = BookmarkUIState(), event = {}, dashboardEvent = {})
}