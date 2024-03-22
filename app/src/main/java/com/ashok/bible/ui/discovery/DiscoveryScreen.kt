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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ashok.bible.R
import com.ashok.bible.data.AppConstants.NO_DATA_FOUND
import com.ashok.bible.data.local.entity.StoryModel
import com.ashok.bible.domain.model.QuotesMappingModel
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
    onBackPress: () -> Unit,
    onClickMoreStory: (List<StoryModel>) -> Unit,
    onClickMoreTopic: (QuotesMappingModel) -> Unit,
    onClickButton: (String?, QuotesMappingModel) -> Unit,
    onClickImage: (Int, List<StoryModel>) -> Unit
) {
    BibleTheme {
        Surface(
            modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.background
        ) {
            val context = LocalContext.current
            var isQuotesEmpty by remember {
                mutableStateOf(false)
            }
            var isStoryEmpty by remember {
                mutableStateOf(false)
            }
            var isStatusEmpty by remember {
                mutableStateOf(false)
            }

            Column {
                TopAppBarView("Discovery") {
                    onBackPress.invoke()
                }


                if (state.quotesData?.isError() == true && state.storyData?.isError() == true && state.statusImagesData?.isError() == true) {
                    EmptyScreen(
                        errorMessage = state.quotesData?.getErrorMessage()?.dataErrorUiText()
                    )
                } else if (isQuotesEmpty && isStoryEmpty && isStatusEmpty) {
                    EmptyScreen(errorMessage = NO_DATA_FOUND)
                }

                LazyColumn(
                    contentPadding = PaddingValues(20.dp)
                ) {
                    item {
                        val quotesData = state.quotesData
                        quotesData?.displayResultNoAnimation(
                            onLoading = { QuotesShimmer() },
                            onSuccess = {
                                //event(DiscoveryUIEvent.QuotesTitlesMapping(quotesData.getSuccessData()))
                                val quotesData = quotesData.getSuccessData()
                                val quotesTitles = quotesData.quotesTitles
                                if (quotesTitles != null)
                                    if (quotesTitles.isNotEmpty()) {
                                        TitleView(title = "Search by Topic", onClick = {
                                            onClickMoreTopic.invoke(quotesData)
                                        })
                                        LazyRow(
                                            horizontalArrangement = Arrangement.spacedBy(10.dp)

                                        ) {
                                            items(quotesTitles) { item ->
                                                ButtonView(
                                                    title = item.title,
                                                    color = item.color
                                                        ?: colorResource(id = R.color.colorAccent)
                                                ) {
                                                    onClickButton.invoke(item.title, quotesData)
                                                }
                                            }
                                        }
                                    } else {
                                        isQuotesEmpty = true
                                    }
                            },
                            onError = {

                            })
                    }
                    item {
                        val storyDataState = state.storyData
                        storyDataState?.displayResultNoAnimation(
                            onLoading = { CardShimmer() },
                            onSuccess = {
                                val storyList = storyDataState.getSuccessData()
                                if (storyList != null)
                                    if (storyList.isNotEmpty()) {
                                        TitleView(title = "New to Faith", onClick = {
                                            onClickMoreStory.invoke(storyList)
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
                                                        onClickImage.invoke(index, storyList)
                                                    })
                                            }
                                        }
                                    } else {
                                        isStoryEmpty = true
                                    }
                            },
                            onError = {})
                    }

                    item {
                        val statusImages = state.statusImagesData
                        statusImages?.displayResultNoAnimation(
                            onLoading = {
                                repeat(5) {
                                    CardShimmer()
                                }
                            },
                            onSuccess = {
                                //event(DiscoveryUIEvent.StatusMapping(statusImages.getSuccessData()))
                                val statusList = statusImages.getSuccessData()
                                if (statusList != null)
                                    if (statusList.isNotEmpty()) {
                                        this@LazyColumn.item {
                                            TitleView(
                                                title = "Bible Word",
                                                isButtonVisible = false,
                                                onClick = {})
                                        }
                                        this@LazyColumn.items(statusList) { post ->
                                            ImageShare(
                                                image = post.url
                                            ) {
                                                ShareUtils.shareUrl(context, it)
                                            }
                                            Spacer(modifier = Modifier.height(10.dp))
                                        }
                                    } else {
                                        isStatusEmpty = true
                                    }

                            },
                            onError = {}
                        )
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
        onBackPress = {},
        onClickMoreStory = {},
        onClickMoreTopic = {},
        onClickButton = { _, _ -> },
        { _, _ -> })
}