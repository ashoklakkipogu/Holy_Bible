package com.ashok.myapplication.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ashok.myapplication.ui.screens.BibleViewScreen
import com.ashok.myapplication.ui.screens.BookmarkScreen
import com.ashok.myapplication.ui.screens.DiscoveryScreen
import com.ashok.myapplication.ui.screens.Screens
import com.ashok.myapplication.ui.screens.LyricScreen
import com.ashok.myapplication.ui.screens.ProfileScreen
import com.ashok.myapplication.ui.viewmodel.HomeViewModel


@Composable
fun dashboardNavGraph(
    navController: NavHostController,
    viewModel: HomeViewModel
) {
    NavHost(
        navController = navController, startDestination = Screens.DashboardRoute.router
    ) {
        Log.i("test", "test.......NavHost")

        dahboardNavGraphBuilder(
            navController = navController,
             viewModel = viewModel
        )
    }
}


fun NavGraphBuilder.dahboardNavGraphBuilder(
    navController: NavController,
    viewModel :HomeViewModel
) {

    navigation(
        startDestination = Screens.Bible.router, route = Screens.DashboardRoute.router
    ) {
        composable(route = Screens.Bible.router) {
            BibleViewScreen(navController = navController, viewModel = viewModel)
        }
        composable(Screens.Bookmark.router) {
            BookmarkScreen(navController, viewModel = viewModel)
        }
        composable(Screens.Lyrics.router) {
            LyricScreen(navController)
        }
        composable(Screens.Discovery.router) {
            DiscoveryScreen(navController)
        }
        composable(Screens.More.router) {
            ProfileScreen(navController)
        }
    }
}
