package com.ashok.myapplication.ui.navgraph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.ashok.myapplication.data.local.entity.LyricsModel
import com.ashok.myapplication.data.local.entity.StoryModel
import com.ashok.myapplication.ui.bibleindex.BibleIndexDetailsScreen
import com.ashok.myapplication.ui.bibleindex.BibleIndexDetailsViewModel
import com.ashok.myapplication.ui.bibleindex.BibleIndexScreen
import com.ashok.myapplication.ui.dashboard.DashboardUiState
import com.ashok.myapplication.ui.lyric.LyricDetails
import com.ashok.myapplication.ui.screens.BibleViewScreen
import com.ashok.myapplication.ui.bookmark.BookmarkScreen
import com.ashok.myapplication.ui.bookmark.BookmarkViewModel
import com.ashok.myapplication.ui.discovery.DiscoveryScreen
import com.ashok.myapplication.ui.discovery.DiscoveryUIState
import com.ashok.myapplication.ui.discovery.DiscoveryViewModel
import com.ashok.myapplication.ui.lyric.LyricScreen
import com.ashok.myapplication.ui.lyric.LyricViewModel
import com.ashok.myapplication.ui.screens.ProfileScreen
import com.ashok.myapplication.ui.dashboard.HomeViewModel
import com.ashok.myapplication.ui.discovery.DiscoveryGridScreen
import com.ashok.myapplication.ui.discovery.DiscoveryStoryDetailsScreen
import com.ashok.myapplication.ui.discovery.DiscoveryTopicDetailsScreen
import com.ashok.myapplication.ui.discovery.model.ImageGrid
import com.ashok.myapplication.ui.utilities.sharedViewModel
import com.ashok.myapplication.ui.viewmodel.SharedViewModel


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
                LyricScreen(state = state, event = viewModel::onEvent, onBackPress = {
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
                        sharedViewModel.storeImageGridList(storyList = state.storyList)
                        navController.navigate(Route.DiscoveryGridDetails.router)
                    },
                    onClickMoreTopic = {
                        sharedViewModel.putTitle("Search by Topic")
                        sharedViewModel.putQuotesMap(state.quotesMap)
                        sharedViewModel.storeImageGridList(quotesTitles = state.quotesTitles)
                        navController.navigate(Route.DiscoveryGridDetails.router)
                    },
                    onClickButton = {
                        sharedViewModel.putTitle(it)
                        sharedViewModel.putQuotes(state.quotesMap[it])
                        navController.navigate(Route.DiscoveryTopicDetails.router)
                    },
                    onClickImage = {
                        sharedViewModel.putTitle(state.storyList[it].title)
                        sharedViewModel.storeImageGridList(storyList = listOf( state.storyList[it]))
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
                        sharedViewModel.putImageGridList(listOf(imageGridListState?.get(it) ?: ImageGrid()))
                        navController.navigate(Route.DiscoveryStoryDetails.router)
                    }
                )
            }

            composable(Route.DiscoveryStoryDetails.router) { entry ->
                val sharedViewModel = entry.sharedViewModel<SharedViewModel>(navController)
                val imageGridState = sharedViewModel.getImageGridList.observeAsState().value
                val title = sharedViewModel.title.collectAsState()

                DiscoveryStoryDetailsScreen(
                    title = title.value,
                    imageGridState?.get(0),
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
                    onBackPress = {
                        navController.popBackStack()
                    }
                )
            }
            composable(Route.More.router) {
                ProfileScreen(navController)
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
