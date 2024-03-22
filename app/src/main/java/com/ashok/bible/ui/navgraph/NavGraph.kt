package com.ashok.bible.ui.navgraph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.ashok.bible.data.local.entity.LyricsModel
import com.ashok.bible.ui.bibleindex.BibleIndexDetailsScreen
import com.ashok.bible.ui.bibleindex.BibleIndexDetailsViewModel
import com.ashok.bible.ui.bibleindex.BibleIndexScreen
import com.ashok.bible.ui.dashboard.DashboardUiState
import com.ashok.bible.ui.lyric.LyricDetails
import com.ashok.bible.ui.screens.BibleViewScreen
import com.ashok.bible.ui.bookmark.BookmarkScreen
import com.ashok.bible.ui.bookmark.BookmarkViewModel
import com.ashok.bible.ui.discovery.DiscoveryScreen
import com.ashok.bible.ui.discovery.DiscoveryUIState
import com.ashok.bible.ui.discovery.DiscoveryViewModel
import com.ashok.bible.ui.lyric.LyricScreen
import com.ashok.bible.ui.lyric.LyricViewModel
import com.ashok.bible.ui.screens.ProfileScreen
import com.ashok.bible.ui.dashboard.HomeViewModel
import com.ashok.bible.ui.discovery.DiscoveryGridScreen
import com.ashok.bible.ui.discovery.DiscoveryStoryDetailsScreen
import com.ashok.bible.ui.discovery.DiscoveryTopicDetailsScreen
import com.ashok.bible.ui.discovery.model.ImageGrid
import com.ashok.bible.ui.utilities.sharedViewModel
import com.ashok.bible.ui.viewmodel.SharedViewModel
import com.ashok.bible.ui.viewmodel.SplashViewModel


@Composable
fun NavGraph(
    navController: NavHostController, homeViewModel: HomeViewModel
) {
    NavHost(
        navController = navController, startDestination = Route.DashboardRoute.router
    ) {
        navigation(
            startDestination = Route.Bible.router, route = Route.DashboardRoute.router
        ) {
            composable(route = Route.Bible.router) {
                val state: DashboardUiState = homeViewModel.state
                BibleViewScreen(
                    navController = navController, state = state, event = homeViewModel::onEvent
                )
            }
            composable(Route.Bookmark.router) {
                val viewModel: BookmarkViewModel = hiltViewModel()
                val state = viewModel.state
                BookmarkScreen(
                    navController,
                    state = state,
                    event = viewModel::onEvent,
                    dashboardEvent = homeViewModel::onEvent,
                )
            }
            composable(Route.Lyrics.router) {
                val viewModel: LyricViewModel = hiltViewModel()
                val state = viewModel.state
                LyricScreen(state = state, onBackPress = {
                    navController.popBackStack()
                }, onClick = {
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        key = LYRIC_OBJ, value = it
                    )
                    navController.navigate(Route.LyricDetails.router)
                })
            }
            composable(Route.LyricDetails.router) {
                val result =
                    navController.previousBackStackEntry?.savedStateHandle?.get<LyricsModel>(
                        LYRIC_OBJ
                    )
                LyricDetails(result) {
                    navController.popBackStack()
                }
            }
            composable(Route.Discovery.router) { entry ->
                val viewModel: DiscoveryViewModel = hiltViewModel()
                val state: DiscoveryUIState = viewModel.state
                val sharedViewModel = entry.sharedViewModel<SharedViewModel>(navController)
                DiscoveryScreen(state,
                    onBackPress = {
                        navController.popBackStack()
                    },
                    onClickMoreStory = {
                        sharedViewModel.putTitle("New to Faith")
                        sharedViewModel.storeImageGridList(storyList = it)
                        navController.navigate(Route.DiscoveryGridDetails.router)
                    },
                    onClickMoreTopic = {
                        sharedViewModel.putTitle("Search by Topic")
                        sharedViewModel.putQuotesMap(it.quotesMap)
                        sharedViewModel.storeImageGridList(quotesTitles = it.quotesTitles)
                        navController.navigate(Route.DiscoveryGridDetails.router)
                    },
                    onClickButton = {title, quotesMap->
                        sharedViewModel.putTitle(title)
                        sharedViewModel.putQuotes(quotesMap.quotesMap?.get(title))
                        navController.navigate(Route.DiscoveryTopicDetails.router)
                    },
                    onClickImage = {pos, storyList->
                        sharedViewModel.storeImageGridList(storyList = storyList)
                        sharedViewModel.putTitle(storyList[pos].title)
                        //val list = listOf(state.storyList?.get(it))
                        sharedViewModel.putGridId(gridPos = pos)
                        navController.navigate(Route.DiscoveryStoryDetails.router)
                    }
                )
            }

            composable(Route.DiscoveryGridDetails.router) { entry ->
                val sharedViewModel = entry.sharedViewModel<SharedViewModel>(navController)
                val imageGridListState = sharedViewModel.getImageGridList.observeAsState().value
                val quotesMapState = sharedViewModel.getQuotesMap.collectAsState()
                DiscoveryGridScreen(imageGridListState,
                    onBackPress = {
                        navController.popBackStack()
                    },
                    onClickButton = {
                        sharedViewModel.putTitle(it)
                        sharedViewModel.putQuotes(quotesMapState.value[it])
                        navController.navigate(Route.DiscoveryTopicDetails.router)
                    },
                    onClickImage = {
                        sharedViewModel.putTitle(
                            imageGridListState?.get(it)?.title
                        )
                        sharedViewModel.putGridId(it)
                        navController.navigate(Route.DiscoveryStoryDetails.router)
                    }
                )
            }

            composable(Route.DiscoveryStoryDetails.router) { entry ->
                val sharedViewModel = entry.sharedViewModel<SharedViewModel>(navController)
                val imageGridState = sharedViewModel.getImageGridList.observeAsState().value
                val title = sharedViewModel.title.collectAsState()
                val pos = sharedViewModel.gridPos.collectAsState()

                DiscoveryStoryDetailsScreen(
                    title = title.value,
                    imageGridState?.get(pos.value),
                    onBackPress = {
                        navController.popBackStack()
                    }
                )
            }
            composable(Route.DiscoveryTopicDetails.router) { entry ->
                val sharedViewModel = entry.sharedViewModel<SharedViewModel>(navController)
                val state = sharedViewModel.getQuotes.collectAsState()
                val title = sharedViewModel.title.collectAsState()

                DiscoveryTopicDetailsScreen(
                    title = title.value,
                    state.value,
                    homeEvent = homeViewModel::onEvent,
                    onBackPress = {
                        navController.popBackStack()
                    }
                )
            }
            composable(Route.More.router) {
                val viewModel: SplashViewModel = hiltViewModel()
                val state = viewModel.state
                ProfileScreen(
                    state = state,
                    homeState = homeViewModel.state,
                    event = viewModel::onEvent
                )
            }
            composable(Route.BibleIndex.router) {
                val state: DashboardUiState = homeViewModel.state
                BibleIndexScreen(
                    navController = navController, state = state, event = homeViewModel::onEvent
                )
            }
            composable(
                Route.BibleIndexDetails.router,
                arguments = listOf(navArgument(name = BOOK_ID) {
                    type = NavType.IntType
                }, navArgument(name = CHAPTER_ID) {
                    type = NavType.IntType
                })
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
                        bookId = bookId, chapterId = chapterId, isScrollTop = it - 1
                    )
                    navController.popBackStack(Route.Bible.router, inclusive = false)
                }
            }

        }
    }
}
