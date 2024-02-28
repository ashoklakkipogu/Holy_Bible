package com.ashok.myapplication.ui.navgraph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.ashok.myapplication.data.entity.LyricsModel
import com.ashok.myapplication.ui.bibleindex.BibleIndexDetailsScreen
import com.ashok.myapplication.ui.bibleindex.BibleIndexDetailsViewModel
import com.ashok.myapplication.ui.bibleindex.BibleIndexScreen
import com.ashok.myapplication.ui.lyric.LyricDetails
import com.ashok.myapplication.ui.screens.BibleViewScreen
import com.ashok.myapplication.ui.screens.BookmarkScreen
import com.ashok.myapplication.ui.screens.DiscoveryScreen
import com.ashok.myapplication.ui.lyric.LyricScreen
import com.ashok.myapplication.ui.lyric.LyricViewModel
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
                val viewModel: LyricViewModel = hiltViewModel()
                val state = viewModel.lyrics
                LyricScreen(
                    state = state,
                    event = viewModel::onEvent,
                    onBackPress = {
                        navController.popBackStack()
                    },
                    onClick = {
                        navController.currentBackStackEntry?.savedStateHandle?.set(key = LYRIC_OBJ, value = it)
                        navController.navigate(Route.LyricDetails.router)
                    }
                )
            }
            composable(Route.LyricDetails.router) {
                val result =
                    navController.previousBackStackEntry?.savedStateHandle?.get<LyricsModel>(
                        LYRIC_OBJ
                    )
                LyricDetails(result){
                    navController.popBackStack()
                }
            }
            composable(Route.Discovery.router) {
                DiscoveryScreen(navController)
            }
            composable(Route.More.router) {
                ProfileScreen(navController)
            }
            composable(Route.BibleIndex.router) {
                BibleIndexScreen(homeViewModel, navController = navController)
            }
            composable(Route.BibleIndexDetails.router,
                arguments = listOf(
                    navArgument(name = BOOK_ID) {
                        type = NavType.IntType
                    },
                    navArgument(name = CHAPTER_ID) {
                        type = NavType.IntType
                    }
                )
            ) {
                val bookId = it.arguments?.getInt(BOOK_ID, 0)
                val chapterId = it.arguments?.getInt(CHAPTER_ID, 0)
                val viewModel: BibleIndexDetailsViewModel = hiltViewModel()

                BibleIndexDetailsScreen(
                    viewModel,
                    navController = navController,
                    bookId = bookId!!,
                    chapterId = chapterId!!,
                    event = viewModel::onEvent
                ) {
                    homeViewModel.getBibleActionForLeftRight(
                        bookId = bookId,
                        chapterId = chapterId,
                        isScrollTop = it - 1
                    )
                    navController.popBackStack(Route.Bible.router, inclusive = false)
                }
            }

        }
    }
}