package com.ashok.myapplication.ui.screens

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
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
import com.ashok.myapplication.ui.utilities.BibleUtils
import com.ashok.myapplication.ui.utilities.BibleUtils.copyBibleVerse
import com.ashok.myapplication.ui.utilities.BibleUtils.shareBibleUrl
import com.ashok.myapplication.ui.utilities.bookmarkInsert
import com.ashok.myapplication.ui.viewmodel.HomeViewModel


@Composable
fun BibleViewScreen(
    navController: NavController,
    headingData: MutableState<BibleModelEntry>,
    viewModel: HomeViewModel
) {
    val state = rememberLazyListState()

    val index by viewModel.scrollIndex
    LaunchedEffect(key1 = index) {
        if (index != -1) {
            Log.i("scrollId", "scrollId.....10......." + index)
            state.scrollToItem(index)
        }
        viewModel.setScrollPos(-1)

    }

    var showSheet by remember { mutableStateOf(false) }
    var showNoteDialog by remember { mutableStateOf(false) }
    var selectedBible by remember { mutableStateOf(BibleModelEntry()) }

    val context = LocalContext.current

    val onItemClick = { model: BibleModelEntry ->
        showSheet = true
        selectedBible = model
    }

    if (showSheet) {
        bottomSheet(onDismiss = {
            showSheet = false
        }, onCircleColor = { color ->
            /*bibleData = bibleData.mapIndexed { j, item ->
                if (selectedBible.bibleID == item.bibleID) {
                    var colorCode = color
                    if (item.selectedBackground == "underline" || item.selectedBackground == colorCode) {
                        colorCode = ""
                        highlightDelete(`bibleVerse` = selectedBible, viewModel = homeViewModel)
                    } else {
                        colorCode = color
                        highlightInsert(
                            colorCode = colorCode,
                            bibleVerse = selectedBible,
                            viewModel = homeViewModel
                        )
                    }
                    item.copy(selectedBackground = colorCode)
                } else item
            }*/
        }, onButtonClick = {
            when (it) {
                "Note" -> {
                    showNoteDialog = true
                }

                "Bookmark" -> {
                    /* bibleData = bibleData.mapIndexed { j, item ->
                         if (selectedBible.bibleID == item.bibleID) {
                             bookmarkInsert(bibleVerse = selectedBible, viewModel = homeViewModel)
                             Toast.makeText(context, "Bookmark", Toast.LENGTH_SHORT).show()
                             item.copy(isBookMark = true)
                         } else item
                     }*/

                    selectedBible.isBookMark = true
                    bookmarkInsert(bibleVerse = selectedBible, viewModel = viewModel)
                    Toast.makeText(context, "Bookmark", Toast.LENGTH_SHORT).show()


                }

                "Share" -> {
                    shareBibleUrl(selectedBible, context)
                }

                "Copy" -> {
                    copyBibleVerse(selectedBible, context)

                }
            }
        }, onGridImgClick = {
            val intent = Intent(context, ShareImageActivity::class.java)
            intent.putExtra("selected_image", it)
            context.startActivity(intent)
        })
    }

    if (showNoteDialog) {
        InputDialogView(title = "Bible Notes",
            placeholder = "Note text",
            onDismiss = { showNoteDialog = false },
            onEnteredText = {

                /*bibleData = bibleData.mapIndexed { j, item ->
                    if (selectedBible.bibleID == item.bibleID) {
                        noteInsert(title = it, bibleVerse = selectedBible, viewModel = homeViewModel)
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                        item.copy(isNote = true)
                    } else item
                }*/
            })
    }

    BibleVerseList(
        biblePager = viewModel.biblePagingSource,
        headingData = headingData,
        state = state,
        onItemClick = onItemClick

    )


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