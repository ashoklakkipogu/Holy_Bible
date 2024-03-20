package com.ashok.bible.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ashok.bible.R

@Composable
fun ButtonView(
    title: String? = null,
    color: Color = colorResource(id = R.color.colorAccent),
    onClick: () -> Unit
) {
    TextButton(
        onClick = { onClick.invoke() },
        modifier = Modifier
            .background(
                color = color,
                shape = RoundedCornerShape(3.dp)
            )

    ) {
        title?.let {
            Text(
                modifier = Modifier.padding(
                    start = 10.dp,
                    top = 10.dp,
                    end = 30.dp,
                    bottom = 10.dp
                ),
                text = it,
                color = Color.White,
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Bold
            )
        }
    }

}

@Preview
@Composable
fun ButtonViewPreview() {
    ButtonView("Test", onClick = {

    })
}