package com.ashok.myapplication.ui.screens.tab

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ashok.myapplication.common.EmptyScreen
import com.ashok.myapplication.data.local.entry.BibleModelEntry
import com.ashok.myapplication.data.local.entry.NoteModelEntry
import com.ashok.myapplication.data.local.model.NoteModel
import com.ashok.myapplication.ui.component.BibleWordListView
import com.ashok.myapplication.ui.utilities.BibleUtils.getUtcToDDMMYYYYHHMMA
import com.ashok.myapplication.ui.utilities.Result
import com.ashok.myapplication.ui.utilities.noteInsert
import com.ashok.myapplication.ui.viewmodel.BookmarkViewModel
import com.ashok.myapplication.ui.viewmodel.HomeViewModel
import com.ashok.myapplication.ui.viewmodel.SplashViewModel
import kotlinx.coroutines.launch

@Composable
fun NoteScreenView(
    viewModel: BookmarkViewModel = hiltViewModel(),
    onClick: (Int) -> Unit
) {
    LaunchedEffect(key1 = true) {
        viewModel.getAllNoteBibleData()
    }


    val noteData = viewModel.noteData.value
    val noteDelete = viewModel.noteDelete.observeAsState().value

    if(noteDelete == true){
        Log.i("data...........", "data...........deleted")

    }
    if (noteData.isEmpty()){
        EmptyScreen()
    }

    NoteRow(
        noteData,
        onClick = {
            onClick.invoke(it.bibleLangIndex.split("-")[1].toInt())
        },
        onClickDelete = { item ->
            viewModel.deleteNoteById(item)
        }
    )
}

@Composable
fun NoteRow(
    data: List<NoteModel>,
    onClick: (NoteModel) -> Unit,
    onClickDelete: (NoteModel) -> Unit
) {

    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(10.dp),
    ) {

        itemsIndexed(data) { index, model ->
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