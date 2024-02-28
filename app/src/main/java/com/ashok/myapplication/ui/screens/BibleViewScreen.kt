package com.ashok.myapplication.ui.screens

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.ashok.myapplication.data.local.entry.BibleModelEntry
import com.ashok.myapplication.ui.activity.ShareImageActivity
import com.ashok.myapplication.ui.component.InputDialogView
import com.ashok.myapplication.ui.component.biblecomponent.BibleVerseList
import com.ashok.myapplication.ui.component.bottomSheet
import com.ashok.myapplication.ui.navgraph.HomeTopView
import com.ashok.myapplication.ui.navgraph.Route
import com.ashok.myapplication.ui.utilities.BibleUtils.copyBibleVerse
import com.ashok.myapplication.ui.utilities.BibleUtils.shareBibleUrl
import com.ashok.myapplication.ui.utilities.bookmarkInsertOrDelete
import com.ashok.myapplication.ui.utilities.highlightInsertOrDelete
import com.ashok.myapplication.ui.utilities.noteInsert
import com.ashok.myapplication.ui.viewmodel.HomeViewModel


@Composable
fun BibleViewScreen(
    navController: NavController,
    viewModel: HomeViewModel,
) {
    val listState = rememberLazyListState()

    val index = viewModel.bibleScrollPos
    LaunchedEffect(index) {
        if (index != -1) {
            listState.scrollToItem(index)
            viewModel.setOrResetBibleScrollPos(-1)
        }

    }

    var showSheet by remember { mutableStateOf(false) }
    var showNoteDialog by remember { mutableStateOf(false) }
    var selectedBible by remember { mutableStateOf(BibleModelEntry()) }
    var headingData by remember { mutableStateOf(BibleModelEntry()) }

    val context = LocalContext.current

    val onItemClick = { model: BibleModelEntry ->
        showSheet = true
        selectedBible = model
    }

    if (showSheet) {
        bottomSheet(
            selectedBible = selectedBible.selectedBackground,
            isBookMark = selectedBible.isBookMark,
            isNote = selectedBible.isNote,
            onDismiss = {
                showSheet = false
            },
            onCircleColor = { color ->
                showSheet = false
                val colorCode =
                    if (/*selectedBible.selectedBackground == "underline" || */selectedBible.selectedBackground == color) {
                        ""
                    } else {
                        color
                    }
                selectedBible.selectedBackground = colorCode
                highlightInsertOrDelete(bibleVerse = selectedBible, viewModel = viewModel)
            },
            onButtonClick = {
                when (it) {
                    "Note" -> {
                        showNoteDialog = true
                    }

                    "Bookmark" -> {
                        selectedBible.isBookMark = !selectedBible.isBookMark
                        showSheet = false
                        bookmarkInsertOrDelete(bibleVerse = selectedBible, viewModel = viewModel)
                    }

                    "Share" -> {
                        shareBibleUrl(selectedBible, context)
                    }

                    "Copy" -> {
                        copyBibleVerse(selectedBible, context)

                    }
                }
            },
            onGridImgClick = {
                val intent = Intent(context, ShareImageActivity::class.java)
                intent.putExtra("selected_image", it)
                intent.putExtra("selected_title", selectedBible.verse)
                context.startActivity(intent)
            })
    }

    if (showNoteDialog) {
        InputDialogView(title = "Bible Notes",
            placeholder = "Note text",
            onDismiss = { showNoteDialog = false },
            onEnteredText = {
                selectedBible.isNote = !selectedBible.isNote
                showSheet = false
                noteInsert(bibleVerse = selectedBible, title = it, viewModel = viewModel)
            })
    }

    Column {
        HomeTopView(
            headingData = headingData,
            leftArrowClick = {
                val obj = headingData
                val book = obj.Book
                val chapter = obj.Chapter - 1
                viewModel.getBibleActionForLeftRight("LEFT", book, chapter)
            }, rightArrowClick = {
                val obj = headingData
                val book = obj.Book
                val chapter = obj.Chapter + 1
                viewModel.getBibleActionForLeftRight("RIGHT", book, chapter)
            }, verseClick = {
                viewModel.expandedState = it.bibleIndex
                navController.navigate(Route.BibleIndex.router)
            }
        )


        BibleVerseList(
            bibleData = viewModel.bibleListData,
            state = listState,
            onItemClick = onItemClick
        ) {
            headingData = it
        }
    }


    /*BibleTheme {
        Surface(
            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
        ) {
            when (bibleData) {
                is Result.Error -> {

                }

                is Result.Loading -> {

                }

                is Result.Success -> {
                    val result = (bibleData as Result.Success<List<BibleModelEntry>>).data
                    bibleVerses(
                        result,
                        scrollState,
                        headingData
                    )
                }
            }
        }
    }*/
}