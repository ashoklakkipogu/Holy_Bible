package com.ashok.myapplication.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.motion.widget.MotionScene.Transition.TransitionOnClick

@Composable
fun ButtonView(
    color: Color = Color.Blue,
    onClick: () -> Unit
) {
    TextButton(
        onClick = { onClick.invoke() },
        modifier = Modifier.background(
            color = color,
            shape = RoundedCornerShape(3.dp)
        )
            .size(120.dp, 45.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Love",
            color = Color.White,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Bold
        )
    }

}

@Preview
@Composable
fun ButtonViewPreview() {
    ButtonView(onClick = {

    })
}