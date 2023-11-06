package com.ashok.myapplication.ui.navigation

import androidx.compose.foundation.lazy.LazyListState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ashok.myapplication.ui.screens.Screens
import com.ashok.myapplication.ui.screens.FavScreen
import com.ashok.myapplication.ui.screens.HomeScreen
import com.ashok.myapplication.ui.screens.LyricScreen
import com.ashok.myapplication.ui.screens.ProfileScreen

fun NavGraphBuilder.dashboardNavGraph(navController: NavController, scrollState: LazyListState) {
    navigation(
        startDestination = Screens.Bible.router,
        route = Screens.DashboardRoute.router
    ) {
        composable(Screens.Bible.router) {
            HomeScreen(navController = navController, scrollState = scrollState)
        }
        composable(Screens.Bookmark.router) {
            LyricScreen(navController)
        }
        composable(Screens.Lyrics.router) {
            FavScreen(navController)
        }
        composable(Screens.Discovery.router) {
            ProfileScreen(navController)
        }
        composable(Screens.More.router) {
            ProfileScreen(navController)
        }
    }
}
