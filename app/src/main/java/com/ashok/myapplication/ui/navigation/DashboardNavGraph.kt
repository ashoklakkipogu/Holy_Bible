package com.ashok.myapplication.ui.navigation

import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.ashok.myapplication.data.local.entry.BibleModelEntry
import com.ashok.myapplication.ui.component.bottomSheet
import com.ashok.myapplication.ui.screens.BookmarkScreen
import com.ashok.myapplication.ui.screens.DiscoveryScreen
import com.ashok.myapplication.ui.screens.Screens
import com.ashok.myapplication.ui.screens.HomeScreen
import com.ashok.myapplication.ui.screens.LyricScreen
import com.ashok.myapplication.ui.screens.ProfileScreen
import com.ashok.myapplication.ui.screens.SCROLL_ID

fun NavGraphBuilder.dashboardNavGraph(
    navController: NavController,
    scrollState: LazyListState,
    headingData: MutableState<BibleModelEntry>,
    clickAction: MutableState<String>
) {

    navigation(
        startDestination = Screens.Bible.router,
        route = Screens.DashboardRoute.router
    ) {
        composable(route = Screens.Bible.router/*,
            arguments = listOf(
                navArgument(name = SCROLL_ID) {
                    type = NavType.IntType
                }
            )*/
        ) { navBackStackEntry ->
            //val scrollId = navBackStackEntry.arguments?.getInt(SCROLL_ID, -1)
            HomeScreen(
                navController = navController,
                scrollState = scrollState,
                headingData = headingData
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
            ProfileScreen(navController)
        }
    }
}
