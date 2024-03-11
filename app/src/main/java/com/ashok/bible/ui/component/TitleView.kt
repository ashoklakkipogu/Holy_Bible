package com.ashok.bible.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun TitleView(
    title:String,
    isButtonVisible:Boolean = true,
    onClick: () -> Unit
) {
    val alpha = if(isButtonVisible) 1f else 0f
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = Color.Black,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        IconButton(onClick = { onClick.invoke() }, modifier = Modifier.alpha(alpha)) {
            Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null)

        }

    }

}

@Preview
@Composable
fun TitleViewViewPreview() {
    TitleView(title = "Search by Topic", onClick = {

    })
}