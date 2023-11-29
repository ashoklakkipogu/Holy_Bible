package com.ashok.myapplication.ui.component.share

import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SlideBarView(title: String, valueRange:ClosedFloatingPointRange<Float>,  onValueChange: (Int) -> Unit) {
    var sliderPosition by remember { mutableFloatStateOf(0f) }
    //Text(text = sliderPosition.toString())
    Text(
        text = title,
        fontSize = 14.sp,
        textAlign = TextAlign.Left,
        color = Color.Black,
    )
    Spacer(modifier = Modifier.height(2.dp))
    Slider(value = sliderPosition,
        valueRange = valueRange,
        onValueChange = {
            sliderPosition = it
            onValueChange.invoke(sliderPosition.toInt())
        })

}

@Preview
@Composable
fun SlideBarViewPrev() {
    SlideBarView(title = "Font Size", valueRange = 10f..30f) {}
}