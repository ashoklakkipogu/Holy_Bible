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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun circleColor(
    onClick: (String) -> Unit
) {
    val items = arrayListOf(
        "#FFCDD2", "#F8BBD0", "#E1BEE7", "#B39DDB",
        "#90CAF9", "#80DEEA", "#80CBC4", "#E6EE9C", "#FFB74D", "#FFEB3B", "#FF5722", "#8D6E63"
    )

    LazyRow(contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp)) {
        items(items) { item ->
            colorView(item) {
                onClick.invoke(it)
            }
            Spacer(modifier = Modifier.width(10.dp))
        }
    }
}

@Composable
fun colorView(color: String, onClick: (String) -> Unit) {
    Box(
        modifier = Modifier
            .clickable {
                onClick.invoke(color)
            }
            .size(40.dp)
            .padding(2.dp)
            .clip(CircleShape)
            .background(Color(color.toColorInt())),
    )
}

@Preview
@Composable
fun circleColorPreview() {
    circleColor {

    }
}