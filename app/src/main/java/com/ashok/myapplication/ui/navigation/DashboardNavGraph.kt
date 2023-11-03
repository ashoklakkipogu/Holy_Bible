package com.ashok.myapplication.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ashok.myapplication.ui.screens.Screens
import com.ashok.myapplication.ui.screens.FavScreen
import com.ashok.myapplication.ui.screens.HomeScreen
import com.ashok.myapplication.ui.screens.LyricScreen
import com.ashok.myapplication.ui.screens.ProfileScreen

fun NavGraphBuilder.dashboardNavGraph(navController: NavController) {
    navigation(
        startDestination = Screens.Home.router,
        route = Screens.DashboardRoute.router
    ) {
        composable(Screens.Home.router) {
            HomeScreen(navController = navController)
        }
        composable(Screens.Lyric.router) {
            LyricScreen(navController)
        }
        composable(Screens.Fav.router) {
            FavScreen(navController)
        }
        composable(Screens.Profile.router) {
            ProfileScreen(navController)
        }
    }
}
