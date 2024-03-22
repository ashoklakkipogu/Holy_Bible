package com.ashok.bible.ui.screens

import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.ashok.bible.data.local.entry.BibleModelEntry
import com.ashok.bible.ui.activity.ShareImageActivity
import com.ashok.bible.ui.common.BackPressHandler
import com.ashok.bible.ui.component.InputDialogView
import com.ashok.bible.ui.dashboard.component.BibleVerseList
import com.ashok.bible.ui.dashboard.component.BottomSheet
import com.ashok.bible.ui.dashboard.DashboardUiEvent
import com.ashok.bible.ui.dashboard.DashboardUiState
import com.ashok.bible.ui.navgraph.HomeTopView
import com.ashok.bible.ui.navgraph.Route
import com.ashok.bible.ui.utilities.BibleUtils.copyBibleVerse
import com.ashok.bible.ui.utilities.BibleUtils.shareBibleUrl


@Composable
fun BibleViewScreen(
    navController: NavController,
    state: DashboardUiState,
    event: (DashboardUiEvent) -> Unit
) {

    val context = LocalContext.current

    val listState = rememberLazyListState()
    val index = state.bibleScrollPos
    LaunchedEffect(index) {
        if (index != -1) {
            listState.scrollToItem(index)
            event(DashboardUiEvent.SetOrResetBibleScrollPos(-1))
        }

    }

    var showSheet by remember { mutableStateOf(false) }
    var showNoteDialog by remember { mutableStateOf(false) }
    var selectedBible by remember { mutableStateOf(BibleModelEntry()) }
    var headingData by remember { mutableStateOf(BibleModelEntry()) }


    val onItemClick = { model: BibleModelEntry ->
        showSheet = true
        selectedBible = model
    }


    if (showSheet) {
        BottomSheet(
            state = state,
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
                event(DashboardUiEvent.HighlightInsertOrDelete(bibleVerse = selectedBible))
            },
            onButtonClick = {
                when (it) {
                    "Note" -> {
                        showNoteDialog = true
                    }

                    "Bookmark" -> {
                        selectedBible.isBookMark = !selectedBible.isBookMark
                        showSheet = false
                        event(DashboardUiEvent.BookmarkInsertOrDelete(bibleVerse = selectedBible))
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
                val str =
                    "${selectedBible.verse} \n ${selectedBible.bibleIndex} ${selectedBible.Chapter}:${selectedBible.Versecount}"
                intent.putExtra("selected_title", str)
                context.startActivity(intent)
            },
            onSoundClick = {
                val str =
                    "${selectedBible.bibleIndex} ${selectedBible.Chapter} ${selectedBible.Versecount} \n ${selectedBible.verse}"
                event(DashboardUiEvent.TextSpeechPlay(str))
            })
    } else {
        event(DashboardUiEvent.TextSpeechStop)
    }

    if (showNoteDialog) {
        InputDialogView(title = "Bible Notes",
            placeholder = "Note text",
            onDismiss = { showNoteDialog = false },
            onEnteredText = {
                selectedBible.isNote = !selectedBible.isNote
                showSheet = false
                event(DashboardUiEvent.NoteInsert(bibleVerse = selectedBible, title = it))
                //noteInsert(bibleVerse = selectedBible, title = it, viewModel = viewModel)
            })
    }

    Column {
        HomeTopView(
            headingData = headingData,
            leftArrowClick = {
                val obj = headingData
                val book = obj.Book
                val chapter = obj.Chapter - 1
                event(DashboardUiEvent.GetBibleActionForLeftRight("LEFT", book, chapter))
            }, rightArrowClick = {
                val obj = headingData
                val book = obj.Book
                val chapter = obj.Chapter + 1
                event(DashboardUiEvent.GetBibleActionForLeftRight("RIGHT", book, chapter))
            }, verseClick = {
                event(DashboardUiEvent.ExpandedState(it.bibleIndex))
                navController.navigate(Route.BibleIndex.router)
            }
        )


        state.bibleData?.displayResult(
            onLoading = { /*TODO*/ },
            onSuccess = {
                state.bibleData?.getSuccessDataOrNull()?.let {
                    BibleVerseList(
                        bibleData = it,
                        state = listState,
                        onItemClick = onItemClick
                    ) {
                        headingData = it
                    }
                }
            },
            onError = {}
        )

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