package com.ashok.myapplication.ui.screens

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ashok.myapplication.data.entity.FavBookMark
import com.ashok.myapplication.data.local.entry.BibleModelEntry
import com.ashok.myapplication.ui.activity.ShareImageActivity
import com.ashok.myapplication.ui.component.InputDialogView
import com.ashok.myapplication.ui.component.biblecomponent.BibleVerseList
import com.ashok.myapplication.ui.component.bottomSheet
import com.ashok.myapplication.ui.navigation.HomeTopView
import com.ashok.myapplication.ui.utilities.BibleUtils
import com.ashok.myapplication.ui.utilities.BibleUtils.copyBibleVerse
import com.ashok.myapplication.ui.utilities.BibleUtils.shareBibleUrl
import com.ashok.myapplication.ui.utilities.bookmarkInsert
import com.ashok.myapplication.ui.utilities.bookmarkInsertOrDelete
import com.ashok.myapplication.ui.viewmodel.HomeViewModel
import com.google.gson.Gson


@Composable
fun BibleViewScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {

    Log.i("page", "page...1............")

    val state = rememberLazyListState()

    val index by viewModel.scrollIndex
    LaunchedEffect(key1 = index) {
        if (index != -1) {
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
    Log.i("page", "page...2............")

   /* LaunchedEffect(Unit) {
        viewModel.getAllFavBookNoteData()
    }
    val allFavBookNote = viewModel.allFavBookNote
    *//*var favBookMark by remember {
        mutableStateOf(listOf<FavBookMark>())
    }*//*

    *//*LaunchedEffect(allFavBookNote) {
        favBookMark = allFavBookNote.value
        Log.i("allFavBookNote", "allFavBookNote..............." + allFavBookNote.value.toString())
    }*//*
    Log.i("allFavBookNote", "allFavBookNote...1............$allFavBookNote")
    var headingData by remember { mutableStateOf(BibleModelEntry()) }*/


    Column {
        HomeTopView(
            headingData = BibleModelEntry(),
            leftArrowClick = {
                /*clickAction.value = "LEFT"
                coroutineScope.launch {
                    val obj = headingData.value
                    val book = obj.Book
                    val chapter = obj.Chapter - 1
                    viewModel.getBibleScrollPosition("LEFT", book, chapter, 1)

                }*/


            }, rightArrowClick = {
                /*clickAction.value = "RIGHT"
                coroutineScope.launch {
                    val obj = headingData.value
                    val book = obj.Book
                    val chapter = obj.Chapter + 1
                    viewModel.getBibleScrollPosition("RIGHT", book, chapter, 1)
                }*/

            }, verseClick = {
                //clickAction.value = "CENTER"
            }
        )


        BibleVerseList(
            biblePager = viewModel.biblePagingSource,
            state = rememberLazyListState(),
            onItemClick = onItemClick,
            onHeadingTitle = {
                //headingData = it
            }
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