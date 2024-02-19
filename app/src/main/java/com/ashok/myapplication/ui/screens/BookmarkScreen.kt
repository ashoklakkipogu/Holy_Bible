package com.ashok.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ashok.myapplication.ui.Route.tab.TabScreen
import com.ashok.myapplication.ui.theme.BibleTheme
import com.ashok.myapplication.ui.viewmodel.HomeViewModel

@Composable
fun BookmarkScreen(navController: NavController, viewModel:HomeViewModel) {
    BibleTheme {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.background
        ) {
            TabScreen(navController, viewModel)
        }
    }
}


@Preview
@Composable
fun BookmarkScreenPreview() {
    BookmarkScreen(rememberNavController(), hiltViewModel())
}