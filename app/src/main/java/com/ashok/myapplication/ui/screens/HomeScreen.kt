package com.ashok.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ashok.myapplication.data.local.entry.BibleModelEntry
import com.ashok.myapplication.ui.theme.BibleTheme
import com.ashok.myapplication.ui.component.bibleVerses
import com.ashok.myapplication.ui.utilities.Result
import com.ashok.myapplication.ui.viewmodel.HomeViewModel


@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavController,
    scrollState: LazyListState,
    headingData: MutableState<BibleModelEntry>
) {
    LaunchedEffect(Unit) {
        homeViewModel.getBibleData()
    }
    val bibleData by homeViewModel.bibleData.observeAsState(
        initial = Result.Loading()
    )

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
