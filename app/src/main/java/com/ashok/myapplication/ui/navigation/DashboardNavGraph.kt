package com.ashok.myapplication.ui.navigation

import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.ashok.myapplication.data.local.entry.BibleModelEntry
import com.ashok.myapplication.ui.screens.BibleViewScreen
import com.ashok.myapplication.ui.screens.BookmarkScreen
import com.ashok.myapplication.ui.screens.DiscoveryScreen
import com.ashok.myapplication.ui.screens.Screens
import com.ashok.myapplication.ui.screens.LyricScreen
import com.ashok.myapplication.ui.screens.ProfileScreen
import com.ashok.myapplication.ui.screens.SCROLL_ID
import com.ashok.myapplication.ui.viewmodel.HomeViewModel


@Composable
fun dashboardNavGraph(
    navController: NavHostController,
    clickAction: MutableState<String>
) {
    NavHost(
        navController = navController, startDestination = Screens.DashboardRoute.router
    ) {
        Log.i("test", "test.......NavHost")

        dahboardNavGraphBuilder(
            navController = navController,
            clickAction = clickAction,
           // viewModel = viewModel
        )
    }
}


fun NavGraphBuilder.dahboardNavGraphBuilder(
    navController: NavController,
    clickAction: MutableState<String>,
   // viewModel :HomeViewModel
) {

    navigation(
        startDestination = Screens.Bible.router, route = Screens.DashboardRoute.router
    ) {

        Log.i("allFavBookNote", "allFavBookNote...test1............")

        composable(
            route = Screens.Bible.router

        ) {

            Log.i("allFavBookNote", "allFavBookNote...test2............")

            BibleViewScreen(
                navController = navController
            )
        }
        composable(Screens.Bookmark.router) {
            BookmarkScreen(navController)
        }
        composable(Screens.Lyrics.router) {
            LyricScreen(navController)
        }
        composable(Screens.Discovery.router) {
            DiscoveryScreen(navController)
        }
        composable(Screens.More.router) {
            Log.i("allFavBookNote", "allFavBookNote...test3............")

            ProfileScreen(navController)
        }
    }
}
