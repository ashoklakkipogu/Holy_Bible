package com.ashok.bible.ui.bookmark.tab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ashok.bible.ui.common.EmptyScreen
import com.ashok.bible.data.local.model.FavModel
import com.ashok.bible.ui.bookmark.BookmarkUIEvent
import com.ashok.bible.ui.bookmark.BookmarkUIState
import com.ashok.bible.ui.component.BibleWordListView
import com.ashok.bible.ui.utilities.BibleUtils.getUtcToDDMMYYYYHHMMA

@Composable
fun FavScreenView(
    state: BookmarkUIState,
    event: (BookmarkUIEvent) -> Unit,
    onClick: (FavModel) -> Unit,
) {
    LaunchedEffect(Unit) {
        event(BookmarkUIEvent.GetAllFavBibleData)
    }
    if (state.favData?.isEmpty() == true) {
        EmptyScreen()
    }
    if (state.favData != null)
        FavRow(
            state.favData,
            onClick = {
                //onClick.invoke(it.bibleLangIndex.split("-")[1].toInt())
                onClick.invoke(it)
            },
            onClickDelete = { item ->
                event(BookmarkUIEvent.DeleteFavById(item))
            }
        )

}

@Composable
fun FavRow(
    data: List<FavModel>,
    onClick: (FavModel) -> Unit,
    onClickDelete: (FavModel) -> Unit
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
fun FavScreenViewPreview() {
    val obj = FavModel()
    obj.createdDate = "2 weeks ago"
    obj.createdDate = "2 weeks ago"
    obj.verse =
        "ఆదియందు దేవుడు భూమ్యాకాశములను సృజించెను. భూమి నిరాకారముగాను శూన్యముగాను ఉండెను; చీకటి అగాధ జలము పైన కమ్మియుండెను"
    FavRow(data = listOf(obj), {}, {})
}