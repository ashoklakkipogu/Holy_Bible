package com.ashok.bible.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.ashok.bible.ui.navgraph.NavGraph
import com.ashok.bible.ui.navgraph.bottomNavigation
import com.ashok.bible.ui.theme.BibleTheme
import com.ashok.bible.ui.dashboard.HomeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: HomeViewModel) {
    val navController = rememberNavController()
    
    Scaffold(/*topBar = {
        topAppBar(currentRoute = currentRoute, headingData = headingData, leftArrowClick = {
            clickAction.value = "LEFT"
            coroutineScope.launch {
                val obj = headingData.value
                val book = obj.Book
                val chapter = obj.Chapter - 1
                viewModel.getBibleScrollPosition("LEFT", book, chapter, 1)

            }


        }, rightArrowClick = {
            clickAction.value = "RIGHT"
            coroutineScope.launch {
                val obj = headingData.value
                val book = obj.Book
                val chapter = obj.Chapter + 1
                viewModel.getBibleScrollPosition("RIGHT", book, chapter, 1)
            }

        }, verseClick = {
            clickAction.value = "CENTER"
        })
    }, */bottomBar = {
        bottomNavigation(navController)

    }) {
        //Log.i("test", "test.......padding")

        Box(modifier = Modifier.padding(it)) {
            NavGraph(navController, viewModel)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainPreview() {
    BibleTheme {
        MainScreen(
            viewModel = viewModel()
        )
    }
}

