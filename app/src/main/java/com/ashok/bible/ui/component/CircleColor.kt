package com.ashok.bible.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun circleColor(
    colors: ArrayList<String>,
    selectedBible: String,
    onClick: (String) -> Unit
) {
    LazyRow(contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp)) {
        items(colors) { item ->
            colorView(item, selectedBible) {
                onClick.invoke(it)
            }
            Spacer(modifier = Modifier.width(10.dp))
        }
    }
}

@Composable
fun colorView(
    color: String,
    selectedBible: String,
    onClick: (String) -> Unit
) {
    if (color == "underline") {
        Box(
            modifier = Modifier
                .size(40.dp)
                .padding(2.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
                .clickable {
                    onClick.invoke(color)
                }
        ) {
            Text(
                text = "U",
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center),
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline
            )
            if (selectedBible == "underline")
                Icon(
                    modifier = Modifier
                        .size(40.dp)
                        .padding(2.dp)
                        .alpha(0.5f),
                    tint = Color.White,
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = ""
                )
        }
    } else

        Box(
            modifier = Modifier
                .size(40.dp)
                .padding(2.dp)
                .clip(CircleShape)
                .background(Color(color.toColorInt()))
                .clickable {
                    onClick.invoke(color)
                },
        ) {
            if (selectedBible == color)
                Icon(
                    modifier = Modifier
                        .size(40.dp)
                        .padding(2.dp)
                        .alpha(0.5f),
                    tint = Color.White,
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = ""
                )
        }

}

@Preview
@Composable
fun circleColorPreview() {
    val colorList = arrayListOf(
        "underline", "#FFCDD2", "#F8BBD0", "#E1BEE7", "#B39DDB",
        "#90CAF9", "#80DEEA", "#80CBC4", "#E6EE9C", "#FFB74D", "#FFEB3B", "#FF5722", "#8D6E63"
    )

    circleColor(colors = colorList, selectedBible = "") {}
}