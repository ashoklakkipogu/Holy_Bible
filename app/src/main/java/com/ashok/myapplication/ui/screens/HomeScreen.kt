package com.ashok.myapplication.ui.screens

import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ashok.myapplication.data.local.entry.BibleModelEntry
import com.ashok.myapplication.ui.component.bibleVerses
import com.ashok.myapplication.ui.viewmodel.HomeViewModel
import kotlinx.coroutines.delay


@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavController,
    headingData: MutableState<BibleModelEntry>,
    scrollId: Int
) {
    val coroutineScope = rememberCoroutineScope()

    /*val bibleData by homeViewModel.bibleData.observeAsState(
        initial = Result.Loading()
    )*/

    /*val secondScreenResult = navController.currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<Int>("scollId")?.observeAsState()
    secondScreenResult?.value?.let {
        coroutineScope.launch {
            Log.i("scrollid", "scrollId......2.....$it")
            delay(1000)
            scrollState.scrollToItem(it)
        }
    }*/
    /*if (scrollState.isScrollInProgress) {
        DisposableEffect(Unit) {
            onDispose {
                println("scroll completed")
                if (scrollId !=null && scrollId !=-1){
                    //LaunchedEffect(scrollId) {
                    *//*coroutineScope.launch {
                        scrollState.scrollToItem(scrollId)
                    }*//*
                    //}
                }
            }
        }
    }*/

   /* if (scrollId !=null && scrollId !=-1){
        LaunchedEffect(scrollId) {
            delay(2000)
            Log.i("data", "scrollId......2....."+scrollId)
            scrollState.scrollToItem(scrollId)
        }
    }*/
    bibleVerses(
        headingData,
        scrollId
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
