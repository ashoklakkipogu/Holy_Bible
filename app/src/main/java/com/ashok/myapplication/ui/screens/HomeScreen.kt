package com.ashok.myapplication.ui.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.ashok.myapplication.data.local.entry.BibleModelEntry
import com.ashok.myapplication.ui.theme.BibleTheme
import com.ashok.myapplication.ui.component.bibleVerses
import com.ashok.myapplication.ui.utilities.Result
import com.ashok.myapplication.ui.viewmodel.HomeViewModel
import kotlinx.coroutines.launch


@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavController,
    scrollState: LazyListState,
    headingData: MutableState<BibleModelEntry>
) {
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        homeViewModel.getBibleData()
    }

    val bibleData by homeViewModel.bibleData.observeAsState(
        initial = Result.Loading()
    )
    val bibleScrollPos by homeViewModel.bibleScrollPos.observeAsState(
        initial = null
    )
    bibleScrollPos?.let { scrollState.animateScrollToItem(it) }
    //println("...............$bibleScrollPos")
   /* LaunchedEffect(Unit) {
        println("...............$bibleScrollPos")
        bibleScrollPos?.let { scrollState.animateScrollToItem(it) }
    }*/
    //Log.i("scrollid", "scrollId......1.....$bibleScrollPos")
    //coroutineScope.launch {
    /*LaunchedEffect(bibleScrollPos) {
        Log.i("scrollid", "scrollId......1.....$bibleScrollPos")
        scrollState.scrollToItem(bibleScrollPos)
    }*/
   // }



    val secondScreenResult = navController.currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<Int>("scollId")?.observeAsState()
    secondScreenResult?.value?.let {
        coroutineScope.launch {
            Log.i("scrollid", "scrollId......1.....$it")
            scrollState.scrollToItem(it)
        }
    }





    BibleTheme {
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
    }
}
