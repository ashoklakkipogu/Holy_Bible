package com.ashok.myapplication.ui.bibleindex.components

import android.icu.text.CaseMap.Title
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.ashok.myapplication.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarView(
    title: String,
    onBackClick:() ->Unit
){
    TopAppBar(
        title = {
            Text(title)
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
