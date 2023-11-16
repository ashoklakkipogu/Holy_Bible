package com.ashok.myapplication.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.ashok.myapplication.ui.component.bottomSheet
import com.ashok.myapplication.ui.navigation.bottomNavigation
import com.ashok.myapplication.ui.navigation.dashboardNavGraph
import com.ashok.myapplication.ui.navigation.topAppBar
import com.ashok.myapplication.ui.theme.BibleTheme
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController,
    currentRoute: String
) {
    val scorllState = rememberLazyListState()
    val scrollBehavior = BottomAppBarDefaults.exitAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            topAppBar(
                currentRoute = currentRoute,
                leftArrowClick = {

                },
                rightArrowClick = {

                },
                verseClick = {

                }
            )
        },
        bottomBar = {
            bottomNavigation(navController, scrollBehavior)

        }
    ) {
        NavHost(
            navController = navController,
            startDestination = Screens.DashboardRoute.router,
            modifier = Modifier.padding(paddingValues = it)
        ) {
            dashboardNavGraph(navController, scorllState)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    BibleTheme {
        MainScreen(
            rememberNavController(),
            currentRoute = Screens.DashboardRoute.router
        )
    }
}

