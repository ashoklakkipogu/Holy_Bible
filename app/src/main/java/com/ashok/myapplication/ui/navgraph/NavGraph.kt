package com.ashok.myapplication.ui.navgraph

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ashok.myapplication.ui.bibleindex.BibleIndexDetailsScreen
import com.ashok.myapplication.ui.bibleindex.BibleIndexScreen
import com.ashok.myapplication.ui.bibleindex.BibleIndexViewModel
import com.ashok.myapplication.ui.screens.BibleViewScreen
import com.ashok.myapplication.ui.screens.BookmarkScreen
import com.ashok.myapplication.ui.screens.DiscoveryScreen
import com.ashok.myapplication.ui.screens.LyricScreen
import com.ashok.myapplication.ui.screens.ProfileScreen
import com.ashok.myapplication.ui.viewmodel.HomeViewModel


@Composable
fun NavGraph(
    navController: NavHostController,
    homeViewModel: HomeViewModel
) {
    NavHost(
        navController = navController, startDestination = Route.DashboardRoute.router
    ) {
        navigation(
            startDestination = Route.Bible.router, route = Route.DashboardRoute.router
        ) {
            composable(route = Route.Bible.router) {
                BibleViewScreen(navController = navController, viewModel = homeViewModel)
            }
            composable(Route.Bookmark.router) {
                BookmarkScreen(navController, viewModel = homeViewModel)
            }
            composable(Route.Lyrics.router) {
                LyricScreen(navController)
            }
            composable(Route.Discovery.router) {
                DiscoveryScreen(navController)
            }
            composable(Route.More.router) {
                ProfileScreen(navController)
            }
            composable(Route.BibleIndex.router) {
                val viewModel:BibleIndexViewModel = hiltViewModel()
                BibleIndexScreen(viewModel, navController=navController, event = viewModel::onEvent)
            }
            composable(Route.BibleIndexDetails.router) {
                val viewModel:BibleIndexViewModel = hiltViewModel()
                BibleIndexDetailsScreen(viewModel, event = viewModel::onEvent){
                    homeViewModel.setOrResetBibleScrollPos(100)
                    navController.popBackStack(Route.Bible.router, inclusive = false)
                }
            }

        }
    }
}