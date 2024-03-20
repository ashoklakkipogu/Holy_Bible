package com.ashok.bible.ui.discovery

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ashok.bible.R
import com.ashok.bible.data.AppConstants.NO_DATA_FOUND
import com.ashok.bible.ui.bibleindex.components.TopAppBarView
import com.ashok.bible.ui.common.EmptyScreen
import com.ashok.bible.ui.common.dataErrorUiText
import com.ashok.bible.ui.component.ButtonView
import com.ashok.bible.ui.component.CardView
import com.ashok.bible.ui.component.ImageShare
import com.ashok.bible.ui.component.TitleView
import com.ashok.bible.ui.discovery.component.CardShimmer
import com.ashok.bible.ui.discovery.component.QuotesShimmer
import com.ashok.bible.ui.discovery.model.ImageGrid
import com.ashok.bible.ui.lyric.LyricEvent
import com.ashok.bible.ui.theme.BibleTheme
import com.ashok.bible.ui.utilities.ShareUtils


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoveryScreen(
    state: DiscoveryUIState,
    event: (DiscoveryUIEvent) -> Unit,
    onBackPress: () -> Unit,
    onClickMoreStory: () -> Unit,
    onClickMoreTopic: () -> Unit,
    onClickButton: (String?) -> Unit,
    onClickImage: (Int) -> Unit
) {
    BibleTheme {
        Surface(
            modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.background
        ) {
            val context = LocalContext.current

            Column {
                TopAppBarView("Discovery") {
                    onBackPress.invoke()
                }


                if (state.quotesData?.isError() == true && state.storyData?.isError() == true && state.statusImagesData?.isError() == true) {
                    EmptyScreen(errorMessage = state.quotesData?.getErrorMessage()?.dataErrorUiText())
                }else if (state.quotesData?.getSuccessDataOrNull().isNullOrEmpty() && state.statusImagesData?.getSuccessDataOrNull().isNullOrEmpty() && state.storyData?.getSuccessDataOrNull().isNullOrEmpty()) {
                    EmptyScreen(errorMessage = NO_DATA_FOUND)
                }

                LazyColumn(
                    contentPadding = PaddingValues(20.dp)
                ) {
                    item {
                        val quotesData = state.quotesData
                        quotesData?.displayResult(
                            onLoading = { QuotesShimmer() },
                            onSuccess = { event(DiscoveryUIEvent.QuotesTitlesMapping(quotesData.getSuccessData())) },
                            onError = {/*quotesData.getErrorMessage().dataErrorUiText()*/ })
                        val quotesTitles = state.quotesTitlesMapping
                        if (!quotesTitles.isNullOrEmpty()) {
                            TitleView(title = "Search by Topic", onClick = {
                                onClickMoreTopic.invoke()
                            })
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(10.dp)

                            ) {
                                items(quotesTitles) { item ->
                                    ButtonView(title = item.title, color = item.color?: colorResource(id = R.color.colorAccent)) {
                                        onClickButton.invoke(item.title)
                                    }
                                }
                            }
                        }
                    }
                    item {
                        val storyData = state.storyData
                        storyData?.displayResult(
                            onLoading = { CardShimmer() },
                            onSuccess = { event(DiscoveryUIEvent.StoryMapping(storyData.getSuccessData())) },
                            onError = {})

                        val storyList = state.storyList
                        if (!storyList.isNullOrEmpty()) {
                            TitleView(title = "New to Faith", onClick = {
                                onClickMoreStory.invoke()
                            })
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(10.dp)

                            ) {
                                itemsIndexed(storyList) { index, item ->
                                    CardView(data = ImageGrid(
                                        title = item.title,
                                        image = item.url
                                    ),
                                        onClickButton = {
                                            //onClickButton.invoke(it)
                                        },
                                        onClickImage = {
                                            onClickImage.invoke(index)
                                        })
                                }
                            }
                        }
                    }

                    item {
                        val statusImages = state.statusImagesData
                        statusImages?.displayResult(onLoading = {
                            repeat(5) {
                                CardShimmer()
                            }
                        }, onSuccess = {
                            event(DiscoveryUIEvent.StatusMapping(statusImages.getSuccessData()))
                        }, onError = {})
                    }

                    val statusList = state.statusList
                    if (!statusList.isNullOrEmpty()) {
                        item {
                            TitleView(title = "Bible Word", isButtonVisible = false, onClick = {})
                        }
                        items(statusList) { post ->
                            ImageShare(
                                image = post.url
                            ) {
                                ShareUtils.shareUrl(context, it)
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                }
            }


        }
    }
}

@Preview
@Composable
fun DiscoveryScreenPreview() {
    DiscoveryScreen(state = DiscoveryUIState(),
        event = {},
        onBackPress = {},
        onClickMoreStory = {},
        onClickMoreTopic = {},
        {},
        {})
}