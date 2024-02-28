package com.ashok.myapplication.ui.lyric

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.ashok.myapplication.common.EmptyScreen
import com.ashok.myapplication.data.entity.LyricsModel
import com.ashok.myapplication.ui.bibleindex.components.TopAppBarView
import com.ashok.myapplication.ui.lyric.component.AnimatedShimmer
import com.ashok.myapplication.ui.lyric.component.LyricCardView
import com.ashok.myapplication.ui.lyric.component.SearchAppBar
import com.ashok.myapplication.ui.theme.BibleTheme
import com.ashok.myapplication.ui.utilities.Result


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LyricScreen(
    state: LyricState,
    event: (LyricEvent) -> Unit,
    onClick:(LyricsModel) -> Unit,
    onBackPress:() -> Unit
) {

    BibleTheme {

        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                TopAppBarView("Lyric") {
                    onBackPress.invoke()
                }
                SearchAppBar()
                when (val data = state.lyrics) {
                    is Result.Error -> {
                        EmptyScreen(errorMessage = data.error.toString())
                    }

                    is Result.Loading -> {
                        ShimmerEffect()
                    }

                    is Result.Success -> {
                        LyricList(data.data){
                            onClick.invoke(it)
                        }
                    }
                }

            }

        }
    }
}

@Composable
fun LyricList(data: List<LyricsModel>, onClick:(LyricsModel) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        items(
            count = data.size,
        ) { index: Int ->
            val model: LyricsModel = data[index]
            val title = if (model.isSecondLan) model.titleEn else model.title
            LyricCardView(
                title = title
            ){
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
    LyricScreen(LyricState().copy(lyrics = Result.Loading()), event = {}, onClick = {}, onBackPress = {})
}