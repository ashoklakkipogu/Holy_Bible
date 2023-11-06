package com.ashok.myapplication.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.ashok.myapplication.ui.navigation.bottomNavigation
import com.ashok.myapplication.ui.navigation.dashboardNavGraph
import com.ashok.myapplication.ui.navigation.topAppBar
import com.ashok.myapplication.ui.theme.BibleTheme


@Composable
fun MainScreen(
    navController: NavHostController,
    currentRoute: String
) {
    val scorllState = rememberLazyListState()

    Scaffold(
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
            bottomNavigation(navController, scorllState)

        },
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