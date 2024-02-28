package com.ashok.myapplication.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ButtonComponent(
    isBookMark: Boolean = false,
    isNote: Boolean = false,
    onClick: (String) -> Unit
) {
    val items = arrayListOf("Share", "Copy", "Note", "Bookmark")
    LazyRow(contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp)) {
        items(items) { item ->
            ButtonView(title = item, isBookMark = isBookMark, isNote = isNote, onClick = {
                onClick.invoke(item)
            })
            Spacer(modifier = Modifier.width(10.dp))
        }
    }
}

@Composable
fun ButtonView(
    title: String,
    isBookMark: Boolean,
    isNote: Boolean,
    onClick: (String) -> Unit
) {
    var color = Color.LightGray
    if (title == "Bookmark"){
        if (isBookMark){
            color = Color.Red
        }
    }
    if (title == "Note"){
        if (isNote){
            color = Color.Red
        }
    }
    Button(
        onClick = { onClick.invoke(title) },
        colors = ButtonDefaults.buttonColors(containerColor = color),
    ) {
        Text(
            text = title,
            color = Color.Black
        )
    }
}

@Preview
@Composable
fun ButtonSheetPreview() {
    ButtonComponent {}
}