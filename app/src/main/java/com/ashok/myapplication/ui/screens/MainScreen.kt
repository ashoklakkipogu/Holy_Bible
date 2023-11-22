package com.ashok.myapplication.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.ashok.myapplication.data.local.entry.BibleModelEntry
import com.ashok.myapplication.ui.navigation.bottomNavigation
import com.ashok.myapplication.ui.navigation.dashboardNavGraph
import com.ashok.myapplication.ui.navigation.topAppBar
import com.ashok.myapplication.ui.theme.BibleTheme
import com.ashok.myapplication.ui.utilities.Result
import com.ashok.myapplication.ui.viewmodel.HomeViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController,
    currentRoute: String,
    viewModel: HomeViewModel
) {
    val scorllState = rememberLazyListState()
    val scrollBehavior = BottomAppBarDefaults.exitAlwaysScrollBehavior()
    val headingData = remember { mutableStateOf(BibleModelEntry()) }
    val clickAction = remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()


    val bibleScrollPos by viewModel.bibleScrollPos.observeAsState(
        initial = null
    )
    println("...............$bibleScrollPos")
    LaunchedEffect(bibleScrollPos){
        bibleScrollPos?.let { scorllState.animateScrollToItem(it) }
    }


    Scaffold(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection), topBar = {
        topAppBar(currentRoute = currentRoute, headingData = headingData, leftArrowClick = {
            clickAction.value = "LEFT"
            coroutineScope.launch {
                val obj = headingData.value
                val book = obj.Book
                val chapter = obj.Chapter-1
                viewModel.getBibleScrollPosition("LEFT", book, chapter, 1)

            }


        }, rightArrowClick = {
            clickAction.value = "RIGHT"
            coroutineScope.launch {
                val obj = headingData.value
                val book = obj.Book
                val chapter = obj.Chapter+1
                viewModel.getBibleScrollPosition("RIGHT", book, chapter, 1)
            }

        }, verseClick = {
            clickAction.value = "CENTER"
        })
    }, bottomBar = {
        bottomNavigation(navController, scrollBehavior)

    }) {

        NavHost(
            navController = navController,
            startDestination = Screens.DashboardRoute.router,
            modifier = Modifier.padding(paddingValues = it)
        ) {
            dashboardNavGraph(
                navController = navController,
                scrollState = scorllState,
                headingData = headingData,
                clickAction = clickAction
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    BibleTheme {
        MainScreen(
            rememberNavController(),
            currentRoute = Screens.DashboardRoute.router,
            viewModel = viewModel()
        )
    }
}

