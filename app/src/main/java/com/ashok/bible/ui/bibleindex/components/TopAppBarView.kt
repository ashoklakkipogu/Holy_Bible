package com.ashok.bible.ui.bibleindex.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarView(
    title: String,
    onBackClick:() ->Unit
){
    TopAppBar(
        title = {
            Text(title, fontSize = 16.sp, lineHeight = 16.sp)
        },
        navigationIcon = {
            IconButton(onClick = {
                onBackClick.invoke()
            }) {
                Icon(Icons.AutoMirrored.Default.ArrowBack, "ArrowBack")
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color.White)
    )
}
