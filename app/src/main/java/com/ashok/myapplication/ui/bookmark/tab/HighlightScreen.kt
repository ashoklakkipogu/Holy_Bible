package com.ashok.myapplication.ui.Route.tab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ashok.myapplication.ui.common.EmptyScreen
import com.ashok.myapplication.data.local.model.HighlightModel
import com.ashok.myapplication.ui.bookmark.BookmarkUIEvent
import com.ashok.myapplication.ui.bookmark.BookmarkUIState
import com.ashok.myapplication.ui.component.BibleWordListView
import com.ashok.myapplication.ui.utilities.BibleUtils.getUtcToDDMMYYYYHHMMA
import com.ashok.myapplication.ui.bookmark.BookmarkViewModel

@Composable
fun HighlightScreenView(
    state: BookmarkUIState,
    event: (BookmarkUIEvent) -> Unit,
    onClick: (HighlightModel) -> Unit
) {
    LaunchedEffect(Unit) {
        event(BookmarkUIEvent.GetAllHighlightBibleData)
    }
    if (state.highlightData?.isEmpty() == true) {
        EmptyScreen()
    }
    if (state.highlightData != null)
        HighlightRow(
            state.highlightData,
            onClick = {
                onClick.invoke(it)
            },
            onClickDelete = { item ->
                event(BookmarkUIEvent.DeleteHighlightById(item))
            }
        )
}

@Composable
fun HighlightRow(
    data: List<HighlightModel>,
    onClick: (HighlightModel) -> Unit,
    onClickDelete: (HighlightModel) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(10.dp)
    ) {

        itemsIndexed(data) { index, model ->
            Column {
                BibleWordListView(
                    title = model.verse,
                    heading = "${model.bibleIndex} ${model.Chapter}:${model.Versecount}",
                    subTitle = "${model.bibleIndex} ${model.Chapter}:${model.Versecount}",
                    dividerColor = Color.Black,
                    timings = getUtcToDDMMYYYYHHMMA(model.createdDate),
                    isVisibleBottom = false,
                    colorCode = model.colorCode,
                    onClick = {
                        onClick.invoke(model)
                    },
                    onClickDelete = {
                        onClickDelete.invoke(model)
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
    HighlightRow(data = listOf(obj), {}, {})
}