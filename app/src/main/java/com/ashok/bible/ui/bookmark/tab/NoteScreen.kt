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
import com.ashok.bible.data.local.model.NoteModel
import com.ashok.bible.ui.bookmark.BookmarkUIEvent
import com.ashok.bible.ui.bookmark.BookmarkUIState
import com.ashok.bible.ui.component.BibleWordListView
import com.ashok.bible.ui.utilities.BibleUtils.getUtcToDDMMYYYYHHMMA

@Composable
fun NoteScreenView(
    state: BookmarkUIState,
    event: (BookmarkUIEvent) -> Unit,
    onClick: (NoteModel) -> Unit
) {
    LaunchedEffect(key1 = true) {
        event(BookmarkUIEvent.GetAllNoteBibleData)
    }


    val noteDataState = state.noteData
    noteDataState?.let {
        val noteData = noteDataState.getSuccessDataOrNull()
        if (!noteData.isNullOrEmpty()){
            NoteRow(
                noteData,
                onClick = {
                    //onClick.invoke(it.bibleLangIndex.split("-")[1].toInt())
                    onClick.invoke(it)
                },
                onClickDelete = { item ->
                    event(BookmarkUIEvent.DeleteNoteById(item))
                }
            )
        }else{
            EmptyScreen()
        }
    }
}

@Composable
fun NoteRow(
    data: List<NoteModel>,
    onClick: (NoteModel) -> Unit,
    onClickDelete: (NoteModel) -> Unit
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(10.dp),
    ) {

        itemsIndexed(data) { _, model ->
            Column {
                BibleWordListView(
                    title = model.verse,
                    subTitle = "${model.bibleIndex} ${model.Chapter}:${model.Versecount}",
                    dividerColor = Color.Black,
                    heading = model.noteName,
                    timings = getUtcToDDMMYYYYHHMMA(model.createdDate),
                    isVisibleBottom = true,
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
fun noteRowPreview() {
    val obj = NoteModel()
    obj.noteName = "You added a Note"
    obj.createdDate = "2 weeks ago"
    obj.createdDate = "2 weeks ago"
    obj.verse =
        "ఆదియందు దేవుడు భూమ్యాకాశములను సృజించెను. భూమి నిరాకారముగాను శూన్యముగాను ఉండెను; చీకటి అగాధ జలము పైన కమ్మియుండెను"
    NoteRow(data = listOf(obj), {}, {})
}