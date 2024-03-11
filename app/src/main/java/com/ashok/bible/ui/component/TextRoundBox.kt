package com.ashok.bible.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ashok.bible.R

@Composable
fun TextRoundBox(
    title: String,
    isSelected: Boolean = true,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clickable {
                onClick.invoke()
            }
            .fillMaxWidth()
            .background(if (isSelected) colorResource(id = R.color.colorAccent) else Color.White)
            .border(
                BorderStroke(
                    1.dp,
                    colorResource(id = R.color.colorAccent)
                )
            ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                color = if (isSelected) Color.White else Color.Black,
                textAlign = TextAlign.Left,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                fontSize = 16.sp,
            )
            if (isSelected)
                Icon(Icons.Default.CheckCircle, "ArrowBack", tint = Color.White)

        }

    }

}

@Composable
@Preview
fun TextRoundBoxPreview() {
    TextRoundBox("Text") {

    }
}