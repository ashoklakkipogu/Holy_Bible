package com.ashok.bible.ui.lyric

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.ashok.bible.data.AppConstants
import com.ashok.bible.ui.common.EmptyScreen
import com.ashok.bible.data.local.entity.LyricsModel
import com.ashok.bible.domain.RequestState
import com.ashok.bible.ui.bibleindex.components.TopAppBarView
import com.ashok.bible.ui.common.dataErrorUiText
import com.ashok.bible.ui.lyric.component.AnimatedShimmer
import com.ashok.bible.ui.lyric.component.LyricCardView
import com.ashok.bible.ui.lyric.component.SearchAppBar
import com.ashok.bible.ui.onboarding.OnboardingUIEvent
import com.ashok.bible.ui.theme.BibleTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LyricScreen(
    state: LyricState,
    event: (LyricEvent) -> Unit,
    onClick: (LyricsModel) -> Unit,
    onBackPress: () -> Unit
) {

    BibleTheme {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.background
        ) {
            var searchText by remember { mutableStateOf("") } // Query for SearchBar

            Column {
                TopAppBarView("Lyric") {
                    onBackPress.invoke()
                }
                SearchAppBar() {
                    searchText = it
                }
                val data = state.lyricData
                data?.displayResult(
                    onLoading = { ShimmerEffect() },
                    onSuccess = { event(LyricEvent.LyricDataMapping(data.getSuccessDataOrNull())) },
                    onError = {
                        EmptyScreen(
                            errorMessage = data.getErrorMessage().dataErrorUiText()
                        )
                    }
                )

                val mappingData = state.lyricMappingData
                if (mappingData != null) {
                    if (mappingData.isNotEmpty()) {
                        LyricList(mappingData, searchText) {
                            onClick.invoke(it)
                        }
                    } else
                        EmptyScreen(errorMessage = AppConstants.NO_DATA_FOUND)
                }
            }

        }
    }
}

@Composable
fun LyricList(data: List<LyricsModel>, searchText: String, onClick: (LyricsModel) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        itemsIndexed(
            items = data.filter {
                it.title.contains(
                    searchText,
                    ignoreCase = true
                ) || it.titleEn.contains(
                    searchText,
                    ignoreCase = true
                )
            }) { _, model ->
            LyricCardView(
                data = model
            ) {
                onClick.invoke(model)
            }
        }
    }
}

@Composable
fun ShimmerEffect() {
    Column {
        repeat(10) {
            AnimatedShimmer()
        }
    }
}


@Preview
@Composable
fun LyricScreenPreview() {
    LyricScreen(
        LyricState(),
        onClick = {},
        onBackPress = {},
        event = {})
}