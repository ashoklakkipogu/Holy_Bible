package com.ashok.myapplication.ui.screens.tab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ashok.myapplication.data.local.entry.BibleModelEntry
import com.ashok.myapplication.data.local.entry.NoteModelEntry
import com.ashok.myapplication.data.local.model.FavModel
import com.ashok.myapplication.data.local.model.HighlightModel
import com.ashok.myapplication.data.local.model.NoteModel
import com.ashok.myapplication.ui.component.BibleHeading
import com.ashok.myapplication.ui.component.BibleVerse
import com.ashok.myapplication.ui.component.BibleWordListView
import com.ashok.myapplication.ui.component.bibleVerses
import com.ashok.myapplication.ui.utilities.BibleUtils.getUtcToDDMMYYYYHHMMA
import com.ashok.myapplication.ui.utilities.Result
import com.ashok.myapplication.ui.viewmodel.BookmarkViewModel
import com.ashok.myapplication.ui.viewmodel.HomeViewModel
import com.ashok.myapplication.ui.viewmodel.SplashViewModel

@Composable
fun HighlightScreenView(
    viewModel: BookmarkViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.getAllHighlightBibleData()
    }
    val highlightData by viewModel.highlightData.observeAsState(
        initial = Result.Loading()
    )
    when (highlightData) {
        is Result.Error -> {

        }

        is Result.Loading -> {

        }

        is Result.Success -> {
            val result = (highlightData as Result.Success<List<HighlightModel>>).data
            HighlightRow(
                result
            )
        }
    }
}

@Composable
fun HighlightRow(data: List<HighlightModel>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(10.dp)
    ) {

        itemsIndexed(data) { index, model ->
            Column {
                BibleWordListView(
                    id=model.id,
                    title = model.verse,
                    heading = "${model.bibleIndex} ${model.Chapter}:${model.Versecount}",
                    subTitle = "${model.bibleIndex} ${model.Chapter}:${model.Versecount}",
                    dividerColor = Color.Black,
                    timings = getUtcToDDMMYYYYHHMMA(model.createdDate),
                    isVisibleBottom = false,
                    colorCode = model.colorCode,
                    onClick = {

                    },
                    onClickDelete = {

                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun HighlightScreenViewPreview() {
    val obj = HighlightModel()
    obj.createdDate = "2 weeks ago"
    obj.createdDate = "2 weeks ago"
    obj.verse =
        "ఆదియందు దేవుడు భూమ్యాకాశములను సృజించెను. భూమి నిరాకారముగాను శూన్యముగాను ఉండెను; చీకటి అగాధ జలము పైన కమ్మియుండెను"
    HighlightRow(data = listOf(obj))
}