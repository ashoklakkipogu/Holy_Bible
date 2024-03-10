package com.ashok.myapplication.ui.discovery

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ashok.myapplication.data.AppConstants.NO_DATA_FOUND
import com.ashok.myapplication.ui.bibleindex.components.TopAppBarView
import com.ashok.myapplication.ui.common.EmptyScreen
import com.ashok.myapplication.ui.component.ButtonView
import com.ashok.myapplication.ui.component.CardView
import com.ashok.myapplication.ui.component.ImageShare
import com.ashok.myapplication.ui.component.TitleView
import com.ashok.myapplication.ui.discovery.component.CardShimmer
import com.ashok.myapplication.ui.discovery.component.QuotesShimmer
import com.ashok.myapplication.ui.discovery.model.ImageGrid
import com.ashok.myapplication.ui.theme.BibleTheme
import com.ashok.myapplication.ui.utilities.BibleUtils
import com.ashok.myapplication.ui.utilities.RandomColors
import com.ashok.myapplication.ui.utilities.ShareUtils


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoveryScreen(
    state: DiscoveryUIState,
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

                if (state.quotesTitles?.isEmpty() == true && state.statusList?.isEmpty() == true && state.storyList?.isEmpty() == true) {
                    EmptyScreen(errorMessage = NO_DATA_FOUND)
                }
                if (state.errorQuote !=null && state.errorStory !=null && state.errorStatus !=null) {
                    EmptyScreen(errorMessage = state.errorQuote)
                }

                LazyColumn(
                    contentPadding = PaddingValues(20.dp)
                ) {
                    item {
                        if (state.isLoadingQuotes) {
                            QuotesShimmer()
                        }
                        val quotesTitles = state.quotesTitles
                        if (quotesTitles !=null) {
                            TitleView(title = "Search by Topic", onClick = {
                                onClickMoreTopic.invoke()
                            })
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(10.dp)

                            ) {
                                items(quotesTitles) { item ->
                                    ButtonView(title = item.title, color = item.color) {
                                        onClickButton.invoke(item.title)
                                    }
                                }
                            }
                        }
                    }
                    item {
                        if (state.isLoadingStory) {
                            CardShimmer()
                        }
                        val storyList = state.storyList
                        if (storyList !=null) {
                            TitleView(title = "New to Faith", onClick = {
                                onClickMoreStory.invoke()
                            })
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(10.dp)

                            ) {
                                itemsIndexed(storyList) {index, item ->
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
                    if (state.isLoadingStatus) {
                        repeat(5) {
                            item {
                                CardShimmer()
                            }
                        }
                    }

                    val statusList = state.statusList
                    if (statusList !=null) {
                        item {
                            TitleView(title = "Bible Word", isButtonVisible = false, onClick = {})
                        }
                        items(statusList) { post ->
                            ImageShare(
                                image = post.url
                            ){
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
        onBackPress = {},
        onClickMoreStory = {},
        onClickMoreTopic = {},
        {},
        {})
}