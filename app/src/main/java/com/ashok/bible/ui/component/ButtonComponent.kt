package com.ashok.bible.ui.component

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

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